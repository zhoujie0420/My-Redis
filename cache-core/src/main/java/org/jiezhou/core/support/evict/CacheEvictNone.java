package org.jiezhou.core.support.evict;

import org.jiezhou.api.ICacheEvict;
import org.jiezhou.api.ICacheEvictContext;

/**
 * @author: jiezhou
 **/

public class CacheEvictNone<K, V> implements ICacheEvict<K, V> {

    @Override
    public boolean evict(ICacheEvictContext<K, V> context) {
        return false;
    }
}
