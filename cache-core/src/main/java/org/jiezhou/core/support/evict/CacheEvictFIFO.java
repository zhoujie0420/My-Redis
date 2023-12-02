package org.jiezhou.core.support.evict;

import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheContext;
import org.jiezhou.api.ICacheEvict;
import org.jiezhou.api.ICacheEvictContext;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: jiezhou
 * 驱除策略 FIFO
 **/

public class CacheEvictFIFO<K, V> implements ICacheEvict<K, V> {


    /**
     * queue
     *
     * @param context
     */
    private Queue<K> queue = new LinkedList<>();

    @Override
    public boolean evict(ICacheEvictContext<K, V> context) {
        boolean result = false;
        final ICache<K, V> cache = context.cache();
        //超过限制，驱除
        if (cache.size() > context.size()) {
            //驱除
            K evictKey = queue.remove();
            // 移除最开始的元素
            cache.remove(evictKey);
            result = true;
        }
        //添加
        queue.add(context.key());
        return result;
    }
}
