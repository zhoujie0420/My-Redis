package org.jiezhou.core.core;

import org.jiezhou.annotation.Refresh;
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

    private  Map<K, V> map;

    private  int sizeLimit;

    private  ICacheEvict<K, V> cacheEvict;


    /**
     * 过期策略
     */
    private  ICacheExpire<K, V> cacheExpire = new CacheExpire<>(this);
    public Cache<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    public Cache<K,V> sizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        return this;
    }

    public Cache<K,V> cacheEvict(ICacheEvict<K, V> cacheEvict) {
        this.cacheEvict = cacheEvict;
        return this;
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

    public ICacheExpire<K, V> cacheExpire() {
        return this.cacheExpire;
    }
    @Override
    public int size() {
        return map.size();
    }

    @Override
   @Refresh
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    @Refresh
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    @Refresh
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    @Refresh
    @SuppressWarnings("unchecked")
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
    @Refresh
    public void clear() {
        map.clear();
    }

    @Override
    @Refresh
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    @Refresh
    public Collection<V> values() {
        return map.values();
    }

    @Override
    @Refresh
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }


}
