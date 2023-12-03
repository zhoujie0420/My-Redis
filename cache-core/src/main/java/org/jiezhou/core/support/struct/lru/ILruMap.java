package org.jiezhou.core.support.struct.lru;

import org.jiezhou.api.ICacheEntry;

public interface ILruMap<K,V> {

    /**
     * 移除最老元素
     */
    ICacheEntry<K,V> removeEldest();

    /**
     * 更新key信息
     */
    void updateKey(K key);

    /**
     * 删除key信息
     */
    void removeKey(K key);

    /**
     * 是否为空
     */
    boolean isEmpty();

    /**
     * 是否包含key
     */
    boolean contains(K key);
}
