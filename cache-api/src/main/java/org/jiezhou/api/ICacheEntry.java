package org.jiezhou.api;

/**
 * 缓存明细信息
 */
public interface ICacheEntry<K, V>{
    K key();

    V value();
}
