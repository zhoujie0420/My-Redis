package org.jiezhou.core.support.evict;

import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheEvictContext;

/**
 * @author: jiezhou
 **/

public class CacheEvictContext<K,V> implements ICacheEvictContext<K,V> {
    /**
     * 新加的key
     */
    private K key;

    /**
     * cache 实现
     */
    private ICache<K,V> cache;

    /**
     * 最大大小
     */
    private int size;

    @Override
    public K key() {
        return key;
    }

    public CacheEvictContext<K, V> key(K key) {
        this.key = key;
        return this;
    }

    @Override
    public ICache<K, V> cache() {
        return cache;
    }

    public CacheEvictContext<K, V> cache(ICache<K, V> cache) {
        this.cache = cache;
        return this;
    }

    @Override
    public int size() {
        return size;
    }

    public CacheEvictContext<K, V> size(int size) {
        this.size = size;
        return this;
    }
}
