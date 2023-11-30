package org.jiezhou.core.api;

import org.jiezhou.api.ICache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author: jiezhou
 * 缓存适配器
 **/

public class CacheAdaptor <K,V> implements ICache<K,V> {
    @Override
    public ICache<K, V> expire(K key, long timeInMills) {
        return null;
    }

    @Override
    public ICache<K, V> expireAt(K key, long timeInMills) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
