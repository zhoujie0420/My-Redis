package org.jiezhou.core.support.persist;


import org.jiezhou.api.ICachePersist;

import java.nio.file.Path;

/**
 * 持久化缓存工具类
 */
public class CachePersists {

    private CachePersists() {
    }

    /**
     * 无任何操作
     */
    public static <K, V> ICachePersist<K, V> none() {
        return new CachePersistNone<>();
    }

    /**
     * RDB
     */
    private static <K, V> ICachePersist<K, V> rdb(final String path) {
        return new CachePersistRDB<>(path);
    }

    /**
     * AOF
     */
    private static <K, V> ICachePersist<K, V> aof(final String path) {
        return new CachePersistAof<>(path);
    }
}
