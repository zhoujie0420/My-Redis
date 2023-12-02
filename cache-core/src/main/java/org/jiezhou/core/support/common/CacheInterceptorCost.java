package org.jiezhou.core.support.common;


import cn.hutool.log.Log;
import org.jiezhou.api.ICacheInterceptor;
import org.jiezhou.api.ICacheInterceptorContext;

/**
 * @author: jiezhou
 **/

public class CacheInterceptorCost<K, V> implements ICacheInterceptor<K, V> {
    private static final Log log = Log.get();

    @Override
    public void before(ICacheInterceptorContext<K, V> context) {
        log.debug("Cost start, method: {}", context.method().getName());

    }

    @Override
    public void after(ICacheInterceptorContext<K, V> context) {
        long cost = context.endTime() - context.startTime();
        log.debug("Cost end, method: {}, cost: {}ms", context.method().getName(), cost);
    }
}
