package org.jiezhou.core.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheEvict;
import org.jiezhou.api.ICacheRemoveListener;
import org.jiezhou.core.core.Cache;
import org.jiezhou.core.support.evict.CacheEvicts;
import org.jiezhou.core.support.listener.CacheRemoveListener;
import org.jiezhou.core.support.listener.CacheRemoveListeners;
import org.jiezhou.core.support.proxy.CacheProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jiezhou
 **/

public final class CacheBs<K, V> {

    private CacheBs() {
    }

    /**
     * 创建实例对象
     */
    public static <K, V> CacheBs<K, V> newInstance() {
        return new CacheBs<>();
    }


    /**
     * map实现
     */
    private Map<K, V> map = new HashMap<K, V>();

    /**
     * 大小限制
     */
    private int size = Integer.MAX_VALUE;

    /**
     * 驱除策略
     */
    private ICacheEvict<K, V> evict = CacheEvicts.fifo();

    /**
     * 删除监听类
     */
    private List<ICacheRemoveListener<K,V>> removeListeners = CacheRemoveListeners.defaults();

    /**
     * map实现
     */
    public CacheBs<K, V> map(Map<K, V> map) {
        ArgUtil.notNull(map, "map");
        this.map = map;
        return this;
    }

    /**
     * 设置size信息
     */
    public CacheBs<K, V> size(int size) {
        ArgUtil.notNegative(size, "size");
        this.size = size - 1;
        return this;
    }


    /**
     * 添加监听策略器
     */
    public CacheBs<K, V> addRemoveListener(ICacheRemoveListener<K,V> listener) {
        ArgUtil.notNull(listener, "listener");

        this.removeListeners.add(listener);
        return this;
    }


    /**
     * 设置驱除策略
     */
    public CacheBs<K, V> evict(ICacheEvict<K, V> cacheEvict) {
        ArgUtil.notNull(evict,"evict");
        this.evict = cacheEvict;
        return this;
    }

    /**
     * 构建缓存信息
     */
    public ICache<K, V> build() {
        Cache<K,V> cache = new Cache<>();
        cache.map(map);
        cache.cacheEvict(evict);
        cache.sizeLimit(size);
        cache.removeListeners(removeListeners);
        return CacheProxy.getProxy(cache);
    }
}
