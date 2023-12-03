package org.jiezhou.core.support.load;

import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheLoad;

/**
 * 加载策略-无
 */
public class CacheLoadNone<K,V> implements ICacheLoad<K,V> {

    @Override
    public void load(ICache<K, V> cache) {
        //nothing...
    }

}