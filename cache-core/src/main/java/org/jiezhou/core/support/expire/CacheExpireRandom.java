package org.jiezhou.core.support.expire;

import cn.hutool.log.Log;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheExpire;
import org.jiezhou.api.ICacheRemoveListener;
import org.jiezhou.api.ICacheRemoveListenerContext;
import org.jiezhou.core.constant.enums.CacheRemoveType;
import org.jiezhou.core.exception.CacheRuntimeException;
import org.jiezhou.core.support.listener.remove.CacheRemoveListenerContext;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CacheExpireRandom<K, V> implements ICacheExpire<K, V> {

    private static final Log log = Log.get();

    /**
     * 单次清空数量限制
     */
    private final int COUNT_LIMIT = 100;

    /**
     * 过期map
     */
    private final Map<K, Long> expireMap = new HashMap<>();

    /**
     * 缓存实现
     */
    private final ICache<K, V> cache;

    /**
     * 是否启用快模式
     */
    private boolean fastMode = false;

    /**
     * 线程执行类
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();


    public CacheExpireRandom(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    /**
     * 初始化任务
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpireThreadRandom(), 10, 10, TimeUnit.MILLISECONDS);
    }

    /**
     * 定时任务
     */
    private class ExpireThreadRandom implements Runnable {
        @Override
        public void run() {
            //判断是否为空
            if (MapUtil.isEmpty(expireMap)) {
                log.debug("Expire map is empty.");
                return;
            }

            //判断是否快模式
            if (fastMode) {
                expireKeys(10L);
            }

            //慢模式
            expireKeys(100L);
        }
    }

    /**
     * 过期信息
     *
     * @param expireAt 过期时间
     */
    private void expireKeys(long expireAt) {
        //设置时间超过100ms
        long timeLimit = System.currentTimeMillis() + expireAt;
        // 恢复fastMode
        fastMode = false;

        // 获取key进行处理
        int count = 0;
        while (true) {
            if (count >= COUNT_LIMIT) {
                log.info("Expire count limit: {}", COUNT_LIMIT);
                return;
            }
            if (System.currentTimeMillis() >= timeLimit) {
                this.fastMode = true;
                log.info("Expire time limit: {}", expireAt);
                return;
            }

            // 随机过期
            K key = getRandomKey();
            Long l = expireMap.get(key);
            boolean expireFlag = expireKey(key, l);
            log.debug("key: {} 过期执行结果 {}", key, expireFlag);

            //2.3 信息更新
            count++;
        }
    }

    /**
     * 随机获取一个key的信息
     *
     * @return key
     */
    private K getRandomKey() {
        ThreadLocalRandom current = ThreadLocalRandom.current();

        Set<K> keySet = expireMap.keySet();
        ArrayList<K> list = new ArrayList<>(keySet);
        int i = current.nextInt(list.size());
        return list.get(i);
    }

    /**
     * 获取随机的一个key信息
     */
    private K getRandomKey2() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        int randomIndex = current.nextInt(expireMap.size());

        // 遍历 keys
        Iterator<K> iterator = expireMap.keySet().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            K key = iterator.next();

            if (count == randomIndex) {
                return key;
            }
            count++;
        }

        // 正常逻辑不会到这里
        throw new CacheRuntimeException("对应信息不存在");
    }

    /**
     * 批量获取多个 key 信息
     *
     * @param sizeLimit 大小限制
     * @return 随机返回的 keys
     */
    private Set<K> getRandomKeyBatch(final int sizeLimit) {
        Random random = ThreadLocalRandom.current();
        int randomIndex = random.nextInt(expireMap.size());

        // 遍历 keys
        Iterator<K> iterator = expireMap.keySet().iterator();
        int count = 0;

        Set<K> keySet = new HashSet<>();
        while (iterator.hasNext()) {
            // 判断列表大小
            if (keySet.size() >= sizeLimit) {
                return keySet;
            }

            K key = iterator.next();
            // index 向后的位置，全部放进来。
            if (count >= randomIndex) {
                keySet.add(key);
            }
            count++;
        }

        // 正常逻辑不会到这里
        throw new CacheRuntimeException("对应信息不存在");
    }

    @Override
    public void expire(K key, long expireAt) {
        expireMap.put(key, expireAt);
    }

    @Override
    public void refreshExpire(Collection<K> keys) {
        if (CollectionUtil.isEmpty(keys)) {
            return;
        }

        // 判断大小，小的作为外循环。一般都是过期的 keys 比较小。
        if (keys.size() <= expireMap.size()) {
            for (K key : keys) {
                Long expireAt = expireMap.get(key);
                expireKey(key, expireAt);
            }
        } else {
            for (Map.Entry<K, Long> entry : expireMap.entrySet()) {
                this.expireKey(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Long expireTime(K key) {
        return expireMap.get(key);
    }

    /**
     * 过期处理 key
     *
     * @param key      key
     * @param expireAt 过期时间
     * @return 是否执行过期
     */
    private boolean expireKey(final K key, final Long expireAt) {
        if (expireAt == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime >= expireAt) {
            expireMap.remove(key);
            // 再移除缓存，后续可以通过惰性删除做补偿
            V removeValue = cache.remove(key);

            // 执行淘汰监听器
            ICacheRemoveListenerContext<K, V> removeListenerContext = CacheRemoveListenerContext.<K, V>newInstance().key(key).value(removeValue).type(CacheRemoveType.EXPIRE.code());
            for (ICacheRemoveListener<K, V> listener : cache.removeListeners()) {
                listener.listen(removeListenerContext);
            }

            return true;
        }

        return false;
    }

}
