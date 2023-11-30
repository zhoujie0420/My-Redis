package org.jiezhou.api;

import java.util.Map;

/**
 * @author: jiezhou
 * 缓存上下文
 **/

public interface ICacheContext<K,V> {
    /**
     * map信息
     */
    Map<K, V> map();

    /**
     * 大小限制
     */
    int size();

    /**
     * 驱除策略
     */
    ICacheEvict<K,V> cacheEvict();


}
