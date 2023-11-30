package org.jiezhou.core.core;

import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheContext;
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

    /**
     * 过期策略
     */
    private final ICacheExpire<K, V> cacheExpire;
    private final Map<K, V> map;

    private final int sizeLimit;

    private final ICacheEvict<K, V> cacheEvict;

    public Cache(ICacheContext<K, V> context) {
        this.map = context.map();
        this.sizeLimit = context.size();
        this.cacheEvict = context.cacheEvict();
        this.cacheExpire = new CacheExpire<>(this);
    }

    @Override
    public ICache<K, V> expire(K key, long timeInMills) {
        long expireAt = System.currentTimeMillis() + timeInMills;
        return expireAt(key, expireAt);
    }

    @Override
    public ICache<K, V> expireAt(K key, long timeInMills) {
        cacheExpire.expire(key, timeInMills);
        return this;
    }

    @Override
    public int size() {
        this.refreshExpireAllKeys();
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        this.refreshExpireAllKeys();
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        this.refreshExpireAllKeys();
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        this.refreshExpireAllKeys();
        return map.containsValue(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        this.refreshExpireAllKeys();
        return map.get(key);
    }

    @Override
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
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        this.refreshExpireAllKeys();
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        this.refreshExpireAllKeys();
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        this.refreshExpireAllKeys();
        return map.entrySet();
    }

    /**
     * 刷新懒过期处理所有的 keys
     * @since 0.0.3
     */
    private void refreshExpireAllKeys() {
        this.cacheExpire.refreshExpire(map.keySet());
    }
}
