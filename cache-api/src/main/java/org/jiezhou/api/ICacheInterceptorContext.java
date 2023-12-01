package org.jiezhou.api;

import java.lang.reflect.Method;

/**
 * @author: jiezhou
 **/

public interface ICacheInterceptorContext<K, V> {
    /**
     * 缓存信息
     */
    ICache<K, V> cache();

    /**
     * 执行的方法信息
     */
    Method method();

    /**
     * 方法参数
     */
    Object[] params();

    /**
     * 方法执行结果
     */
    Object result();

    /**
     * 开始时间
     */
    long startTime();

    /**
     * 结束时间
     */
    long endTime();

}
