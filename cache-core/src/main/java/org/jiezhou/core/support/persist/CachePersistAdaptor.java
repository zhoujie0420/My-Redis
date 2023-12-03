package org.jiezhou.core.support.persist;

import org.jiezhou.api.ICache;
import org.jiezhou.api.ICachePersist;

import java.util.concurrent.TimeUnit;

/**
 * 缓存持久化-适配器
 * @param <K>
 * @param <V>
 */
public class CachePersistAdaptor <K,V> implements ICachePersist<K,V> {

    /**
     * 持久化
     * @param cache
     */
    @Override
    public void persist(ICache<K, V> cache) {

    }

    @Override
    public long delay() {
        return this.period()    ;
    }

    @Override
    public long period() {
        return 1;
    }

    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.SECONDS;
    }
}
