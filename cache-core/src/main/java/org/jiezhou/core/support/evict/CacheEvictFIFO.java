package org.jiezhou.core.support.evict;

import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheContext;
import org.jiezhou.api.ICacheEvict;
import org.jiezhou.api.ICacheEvictContext;
import org.jiezhou.core.model.CacheEntry;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: jiezhou
 * 驱除策略 FIFO
 **/


public class CacheEvictFIFO<K,V> extends AbstractCacheEvict<K,V> {

    /**
     * queue 信息
     *
     */
    private final Queue<K> queue = new LinkedList<>();

    @Override
    public CacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        CacheEntry<K, V> result = null;

        final ICache<K, V> cache = context.cache();
        // 超过限制，执行移除
        if (cache.size() >= context.size()) {
            K evictKey = queue.remove();
            // 移除最开始的元素
            V evictValue = cache.remove(evictKey);
            result = new CacheEntry<>(evictKey, evictValue);
        }

        // 将新加的元素放入队尾
        final K key = context.key();
        queue.add(key);

        return result;
    }

}
