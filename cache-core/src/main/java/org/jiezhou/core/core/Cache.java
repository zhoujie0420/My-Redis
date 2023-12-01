package org.jiezhou.core.core;

import org.jiezhou.annotation.CacheInterceptor;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheEvict;
import org.jiezhou.api.ICacheExpire;
import org.jiezhou.core.support.evict.CacheEvictContext;
import org.jiezhou.core.support.expire.CacheExpire;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author: jiezhou
 **/

public class Cache<K, V> implements ICache<K, V> {

    private Map<K, V> map;

    private int sizeLimit;

    private ICacheEvict<K, V> cacheEvict;


    /**
     * 过期策略
     */
    private ICacheExpire<K, V> cacheExpire = new CacheExpire<>(this);

    public Cache<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    public Cache<K, V> sizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        return this;
    }

    public Cache<K, V> cacheEvict(ICacheEvict<K, V> cacheEvict) {
        this.cacheEvict = cacheEvict;
        return this;
    }

    @Override
    @CacheInterceptor
    public ICache<K, V> expire(K key, long timeInMills) {
        long expireAt = System.currentTimeMillis() + timeInMills;
        return expireAt(key, expireAt);
    }

    @Override
    @CacheInterceptor
    public ICache<K, V> expireAt(K key, long timeInMills) {
        cacheExpire.expire(key, timeInMills);
        return this;
    }

    @CacheInterceptor
    @Override
    public ICacheExpire<K, V> cacheExpire() {
        return this.cacheExpire;
    }

    @Override
    @CacheInterceptor
    public int size() {
        return map.size();
    }

    @Override
    @CacheInterceptor
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    @CacheInterceptor(refresh = true)
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    @CacheInterceptor
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    @CacheInterceptor(refresh = true)
    public V put(K key, V value) {
        //1 尝试驱除
        CacheEvictContext<K, V> context = new CacheEvictContext<>();
        context.key(key).size(sizeLimit).cache(this);
        cacheEvict.evict(context);

        //2.判断驱除后的信息
        if (isSizeLimit()) {
            throw new IllegalStateException("size limit");
        }
        //3.添加
        return map.put(key, value);
    }

    /**
     * 判断是否超过大小限制
     * <p>
     * key whose mapping is to be removed from the map
     *
     * @return
     */
    private boolean isSizeLimit() {
        return this.size() > this.sizeLimit;
    }

    @Override
    @CacheInterceptor
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    @CacheInterceptor
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    @CacheInterceptor(refresh = true)
    public void clear() {
        map.clear();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public Collection<V> values() {
        return map.values();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }


}
