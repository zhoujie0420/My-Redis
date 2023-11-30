package org.jiezhou.api;

/**
 * @author: jiezhou
 **/

public interface ICacheEvict<K,V> {
    /**
     * 驱除策略
     */
    void evict(final ICacheEvictContext<K,V> context);
}
