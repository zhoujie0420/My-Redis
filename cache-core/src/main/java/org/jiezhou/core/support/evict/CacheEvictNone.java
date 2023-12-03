package org.jiezhou.core.support.evict;

import org.jiezhou.api.ICacheEntry;
import org.jiezhou.api.ICacheEvict;
import org.jiezhou.api.ICacheEvictContext;

/**
 * @author: jiezhou
 **/

public class CacheEvictNone<K,V> extends AbstractCacheEvict<K,V> {

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        return null;
    }

}