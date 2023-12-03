package org.jiezhou.core.support.interceptor.evict;


import cn.hutool.log.Log;
import org.jiezhou.api.ICacheEvict;
import org.jiezhou.api.ICacheInterceptor;
import org.jiezhou.api.ICacheInterceptorContext;

import java.lang.reflect.Method;

/**
 * 驱除策略拦截器
 */
public class CacheInterceptorEvict<K,V> implements ICacheInterceptor<K, V> {

    private static final Log log = Log.get();

    @Override
    public void before(ICacheInterceptorContext<K, V> context) {
    }

    @Override
    @SuppressWarnings("all")
    public void after(ICacheInterceptorContext<K, V> context) {
        ICacheEvict<K, V> evict = context.cache().evict();

        Method method = context.method();
        final K key = (K) context.params()[0];
        if ("remove".equals(method.getName())) {
            evict.removeKey(key);
        } else {
            evict.updateKey(key);
        }
    }
}