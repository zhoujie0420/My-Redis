package org.jiezhou.api;

import java.util.Map;

/**
 * @author: jiezhou
 **/

public interface ICache<K,V> extends Map<K,V> {
    /** 设置过期时间
     * 如果key不存在，break
     *
     */
    ICache<K,V> expire(final K key, final long timeInMills);


    /**
     * 在指定的时间过期
     */
    ICache<K,V> expireAt(final K key, final long timeInMills);

}
