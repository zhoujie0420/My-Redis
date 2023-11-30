package org.jiezhou.core.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheEvict;
import org.jiezhou.core.core.Cache;
import org.jiezhou.core.support.evict.CacheEvicts;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiezhou
 **/

public final class CacheBs<K, V> {

    private CacheBs() {
    }

    /**
     * 创建实例对象
     */
    public static <K, V> CacheBs<K, V> newInstance() {
        return new CacheBs<>();
    }


    /**
     * map实现
     */
    private Map<K, V> map = new HashMap<K, V>();

    /**
     * 大小限制
     */
    private int size = Integer.MAX_VALUE;

    /**
     * 驱除策略
     */
    private ICacheEvict<K, V> evict = CacheEvicts.fifo();

    /**
     * map实现
     */
    public CacheBs<K, V> map(Map<K, V> map) {
        ArgUtil.notNull(map, "map");
        this.map = map;
        return this;
    }

    /**
     * 设置size信息
     */
    public CacheBs<K, V> size(int size) {
        ArgUtil.notNegative(size, "size");
        this.size = size - 1;
        return this;
    }

    /**
     * 设置驱除策略
     */
    public CacheBs<K, V> cacheEvict(ICacheEvict<K, V> cacheEvict) {
        this.evict = cacheEvict;
        return this;
    }

    /**
     * 构建缓存信息
     */
    public ICache<K, V> build() {
        CacheContext<K, V> context = new CacheContext<>();
        context.cacheEvict(evict);
        context.map(map);
        context.size(size);
        return new Cache<>(context);
    }
}
