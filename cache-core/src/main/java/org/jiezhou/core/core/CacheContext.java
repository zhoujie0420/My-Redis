package org.jiezhou.core.core;

import org.jiezhou.api.ICacheContext;
import org.jiezhou.api.ICacheEvict;

import java.util.Map;

public class CacheContext <K,V> implements ICacheContext<K,V> {


    private Map<K, V> map;

    private int size;

    private ICacheEvict<K,V> cacheEvict;
    @Override
    public Map<K, V> map() {
        return map;
    }

    public CacheContext<K,V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    @Override
    public int size() {
        return 0;
    }

    public CacheContext<K,V> size(int size) {
        this.size = size;
        return this;
    }

    @Override
    public ICacheEvict<K, V> cacheEvict() {
        return null;
    }

    public CacheContext<K,V> cacheEvict(ICacheEvict<K,V> cacheEvict) {
        this.cacheEvict = cacheEvict;
        return this;
    }
}
