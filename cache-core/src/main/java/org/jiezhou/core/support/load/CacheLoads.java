package org.jiezhou.core.support.load;


import org.jiezhou.api.ICacheLoad;

/**
 * 加载工具类
 */
public final class CacheLoads {
    public CacheLoads() {
    }

    /**
     * 无加载策略
     */
    public static <K, V> ICacheLoad<K, V> none() {
        return new CacheLoadNone<>();
    }

    /**
     * RDB
     */
    public static <K, V> ICacheLoad<K, V> rdb(final String path) {
        return new CacheLoadRdb<>(path);
    }

    /**
     * AOF
     */
    public static <K, V> ICacheLoad<K, V> aof(final String path) {
        return new CacheLoadAof<>(path);
    }
}
