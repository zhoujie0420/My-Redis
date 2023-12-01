package org.jiezhou.core.support.proxy.bs;

import org.jiezhou.annotation.CacheInterceptor;
import org.jiezhou.api.ICache;

import java.lang.reflect.Method;

/**
 * @author: jiezhou
 **/

public interface ICacheProxyBsContext {
    /**
     * 拦截器
     */
    CacheInterceptor interceptor();
    /**
     * 获取代理对象信息
     */
    ICache target();

    /**
     * 目标对象
     */
    ICacheProxyBsContext target(ICache target);

    /**
     * 参数信息
     */
    Object[] params();


    /**
     * 方法信息
     * @return
     */
    Method method();

    /**
     * 方法执行
     */
    Object process() throws Throwable;


}
