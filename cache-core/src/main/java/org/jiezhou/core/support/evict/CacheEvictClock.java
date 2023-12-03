package org.jiezhou.core.support.evict;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.jiezhou.api.ICacheEntry;
import org.jiezhou.api.ICacheEvictContext;
import org.jiezhou.core.support.struct.lru.ILruMap;
import org.jiezhou.core.support.struct.lru.Impl.LruMapCircleList;

/**
 * 淘汰策略- clock算法
 *
 * @param <K>
 * @param <V>
 */
public class CacheEvictClock<K, V> extends AbstractCacheEvict<K, V> {

    private static final Log LOG = LogFactory.get();

    /**
     * 循环列表
     */
    private final ILruMap<K, V> circleList;

    public CacheEvictClock() {
        this.circleList = new LruMapCircleList<>();
    }

    @Override
    public void updateKey(K key) {

    }

    @Override
    public void removeKey(K key) {

    }

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        return null;
    }
}

