package org.jiezhou.core.core;

import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheContext;
import org.jiezhou.api.ICacheEvict;
import org.jiezhou.core.support.evict.CacheEvictContext;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author: jiezhou
 **/

public class Cache<K, V> implements ICache<K, V> {

    private final Map<K, V> map;

    private final int sizeLimit;

    private final ICacheEvict<K, V> cacheEvict;

    public Cache(ICacheContext<K, V> context) {
        this.map = context.map();
        this.sizeLimit = context.size();
        this.cacheEvict = context.cacheEvict();
    }

    @Override
    public ICache<K, V> expire(K key, long timeInMills) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICache<K, V> expireAt(K key, long timeInMills) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {

        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
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
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
