package org.jiezhou.core.support.expire;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheExpire;
import org.jiezhou.api.ICacheRemoveListener;
import org.jiezhou.core.constant.enums.CacheRemoveType;
import org.jiezhou.core.support.listener.CacheRemoveListenerContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiezhou
 * 缓存过期-普通策略
 **/

public class CacheExpire<K, V> implements ICacheExpire<K, V> {

    /**
     * 单次清空的数量限制
     *
     * @param key
     * @param expireAt
     */
    private final int LIMIT = 100;

    /**
     * 过期map
     *
     * @param key
     * @param expireAt
     */
    private final Map<K, Long> expireMap = new HashMap<>();

    /**
     * 缓存实现
     *
     * @param key
     * @param expireAt
     */
    private final ICache<K, V> cache;

    /**
     * 线程执行类
     *
     * @param key
     * @param expireAt
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public CacheExpire(ICache<K, V> cache) {
        this.init();
        this.cache = cache;
    }

    /**
     * 初始化任务
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpireThread(), 100, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * 定时任务
     *
     */
    private class ExpireThread implements Runnable {
        @Override
        public void run() {
            if (MapUtil.isEmpty(expireMap)) {
                return;
            }

            int count = 0;
            for (Map.Entry<K, Long> entry : expireMap.entrySet()) {
                if (count >= LIMIT) {
                    break;
                }
                expirKey(entry.getKey(), entry.getValue());
                count++;
            }
        }
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

        // 判断大小
        if (keys.size() < expireMap.size()) {
            for (K key : keys) {
                expireMap.remove(key);
            }
        } else {
            for (Map.Entry<K, Long> entry : expireMap.entrySet()) {
                if (keys.contains(entry.getKey())) {
                    this.expirKey(entry.getKey(), entry.getValue());
                }
            }
        }
    }


    /**
     * 执行过期操作
     */
    private void expirKey(final K key, final Long expireAt) {
        if (expireAt == null) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if(currentTimeMillis >= expireAt) {
            expireMap.remove(key);
            // 再移除缓存，后续通过惰性删除做补偿
            V removeValue = cache.remove(key);
            //执行淘汰监听器
            CacheRemoveListenerContext<K, V> removeListenerContext = CacheRemoveListenerContext.<K, V>newInstance()
                    .key(key)
                    .value(removeValue)
                    .type(CacheRemoveType.EXPIRE.code());

            for(ICacheRemoveListener<K,V> listener : cache.removeListeners()) {
                listener.listen(removeListenerContext);
            }
        }
    }

    /**
     * 过期处理
     */
    private void expireKey(final K key) {
        Long expireAt = expireMap.get(key);
        if (expireAt == null) {
            return;
        }

        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis >= expireAt) {
            expireMap.remove(key);
        }

    }
}
