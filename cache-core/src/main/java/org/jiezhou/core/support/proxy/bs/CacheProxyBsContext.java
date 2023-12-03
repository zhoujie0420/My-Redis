package org.jiezhou.core.support.proxy.bs;

import org.jiezhou.annotation.CacheInterceptor;
import org.jiezhou.api.ICache;

import java.lang.reflect.Method;

/**
 * @author: jiezhou
 **/

public class CacheProxyBsContext implements ICacheProxyBsContext {


    /**
     * 目标
     */
    private ICache target;

    /**
     * 入参
     *
     * @return
     */
    private Object[] params;

    /**
     * 方法
     *
     * @return
     */
    private Method method;

    /**
     * 拦截器
     * @return
     */
    private CacheInterceptor interceptor;

    /**
     * 新建对象
     *
     * @return
     */
    public static CacheProxyBsContext newInstance() {
        return new CacheProxyBsContext();
    }


    @Override
    public ICache target() {
        return target;
    }

    @Override
    public CacheProxyBsContext target(ICache target) {
        this.target = target;
        return this;
    }

    @Override
    public Object[] params() {
        return params;
    }

    public CacheProxyBsContext params(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public Object process() throws Throwable {
        return this.method.invoke(target, params);
    }

    @Override
    public CacheInterceptor interceptor() {
        return interceptor;
    }

    @Override
    public Method method() {
        return method;
    }

    public CacheProxyBsContext method(Method method) {
        this.method = method;
        this.interceptor = method.getAnnotation(CacheInterceptor.class);
        return this;
    }


}
