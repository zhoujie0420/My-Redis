package org.jiezhou.api;


import java.util.concurrent.TimeUnit;

/**
 * 持久化缓存接口
 */
public interface ICachePersist<K,V> {

    /**
     * 持久化缓存信息
     */
    void persist(final ICache<K,V> cache);

    /**
     * 延迟时间
     */
    long delay();

    /**
     * 周期
     */
    long period();

    /**
     *时间单位
     */
    TimeUnit timeUnit();
}
