package org.jiezhou.core.support.evict;

/**
 * @author: jiezhou
 * 丢弃策略
 **/

public final class CacheEvicts {
    private CacheEvicts() {
    }

    /**
     * 无策略
     */
    public static <K, V> CacheEvictNone<K, V> none() {
        return new CacheEvictNone<>();
    }

    /**
     * 先进先出
     */
    public static <K, V> CacheEvictFIFO<K, V> fifo() {
        return new CacheEvictFIFO<>();
    }
}
