package org.jiezhou.api;

import java.util.Collection;
import java.util.Collections;

/**
 * 缓存过期接口
 * @author: jiezhou
 **/

public interface ICacheExpire<K, V> {
    /**
     * 指定过期信息
     */
    void expire(final K key, final long expireAt);

    /**
     * 惰性删除中需要处理的keys
     */
    void refreshExpire(final Collection<K> keys);

    /**
     * 待过期的key
     */
    Long expireTime(final K key);
}
