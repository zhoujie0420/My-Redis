package org.jiezhou.api;

import java.util.Collection;
import java.util.Collections;

/**
 * @author: jiezhou
 **/

public interface ICacheExpire<K, V> {
    /**
     * 指定过期时间
     */
    void expire(final K key, final long expireAt);

    /**
     * 惰性删除中需要处理的keys
     */
    void refreshExpire(final Collection<K> keys);
}
