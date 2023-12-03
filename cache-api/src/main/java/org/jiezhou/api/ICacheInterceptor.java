package org.jiezhou.api;

/**
 * @author: jiezhou
 * 拦截器接口
 *
 * 耗时统计
 * 监听器
 **/

public interface ICacheInterceptor<K,V> {
    /**
     * 方法执行前
     */
    void before(ICacheInterceptorContext<K,V> context);

    /**
     * 方法执行后
     */
    void after(ICacheInterceptorContext<K,V> context);
}
