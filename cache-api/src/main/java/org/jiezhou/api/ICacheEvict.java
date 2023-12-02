package org.jiezhou.api;

/**
 * @author: jiezhou
 **/

public interface ICacheEvict<K,V> {
    /**
     * 驱除策略
     */
    boolean evict(final ICacheEvictContext<K,V> context);
}
