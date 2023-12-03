package org.jiezhou.core.model;

import org.jiezhou.api.ICacheEntry;

public class CacheEntry <K,V> implements ICacheEntry<K,V> {


    private final K key;

    private final V value;

    public CacheEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 新建元素
     * @return
     */
    public static <K,V> CacheEntry<K,V> of(K key,V value) {
        return new CacheEntry<>(key, value);
    }
    @Override
    public K key() {
        return key;
    }

    @Override
    public V value() {
        return value;
    }

    @Override
    public String toString() {
        return "CacheEntry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
