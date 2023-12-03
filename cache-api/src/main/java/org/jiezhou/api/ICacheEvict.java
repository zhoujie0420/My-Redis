package org.jiezhou.api;

/**
 * 驱除策略
 *
 * @author: jiezhou
 **/

public interface ICacheEvict<K, V> {
    /**
     * 驱除策略
     */
    ICacheEntry<K,V> evict(final ICacheEvictContext<K, V> context);

    /**
     * 更新key 信息
     */
    void updateKey(final K key);

    /**
     * 删除key信息
     */
    void removeKey(final K key);

}
