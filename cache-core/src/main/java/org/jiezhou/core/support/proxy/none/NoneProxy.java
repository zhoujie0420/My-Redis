package org.jiezhou.core.support.proxy.none;

import org.jiezhou.core.support.proxy.ICacheProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: jiezhou
 *
 * 没有代理
 **/

public class NoneProxy implements InvocationHandler, ICacheProxy {
    /**
     * 代理对象
     */
    private final Object target;
    private NoneProxy(Object target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxy,args);
    }

    /**
     * 返回原始对象，没有代理
     * @return
     */
    @Override
    public Object proxy() {
        return this.target;
    }
}
