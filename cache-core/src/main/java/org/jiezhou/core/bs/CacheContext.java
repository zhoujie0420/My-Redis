package org.jiezhou.core.bs;

import org.jiezhou.api.ICacheContext;
import org.jiezhou.api.ICacheEvict;

import java.util.Map;

/**
 * @author: jiezhou
 * 缓存上下文
 **/

public class CacheContext<K, V> implements ICacheContext<K, V> {
    /**
     * map信息
     */
    private Map<K, V> map;

    /**
     * 大小限制
     */
    private int size;


    /**
     * 驱除策略
     */
    private ICacheEvict<K,V> cacheEvict;
    @Override
    public Map<K, V> map() {
        return map;
    }
    public CacheContext<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }
    @Override
    public int size() {
        return this.size;
    }

    public CacheContext<K, V> size(int size) {
        this.size = size;
        return this;
    }
    @Override
    public ICacheEvict<K, V> cacheEvict() {
        return cacheEvict;
    }
    public CacheContext<K, V> cacheEvict(ICacheEvict<K, V> cacheEvict) {
        this.cacheEvict = cacheEvict;
        return this;
    }
}
