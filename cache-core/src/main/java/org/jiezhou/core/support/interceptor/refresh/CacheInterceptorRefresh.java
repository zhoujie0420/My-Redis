package org.jiezhou.core.support.interceptor.refresh;

import cn.hutool.log.Log;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheInterceptor;
import org.jiezhou.api.ICacheInterceptorContext;

/**
 * @author: jiezhou
 * 刷新策略
 **/

public class CacheInterceptorRefresh <K,V> implements ICacheInterceptor<K,V> {
    private static final Log log = Log.get();

    @Override
    public void before(ICacheInterceptorContext<K, V> context) {
        log.debug("Refresh start");
        final ICache<K,V> cache = context.cache();
        cache.expire().refreshExpire(cache.keySet());
    }

    @Override
    public void after(ICacheInterceptorContext<K, V> context) {

    }
}
