package org.jiezhou.api;

/**
 * @author: jiezhou
 * 驱除策略
 *
 * 1.新加的key
 * 2.map 实现
 * 3.淘汰监听器
 **/

public interface ICacheEvictContext<K,V> {

    /**
     * 新加的key
     */
    K key();

    /**
     * cache 实现
     */
    ICache<K,V> cache();

    /**
     * 获取大小
     */
    int size();
}
