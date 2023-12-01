package org.jiezhou.core.support.proxy.cglib;

import com.github.houbb.heaven.reflect.exception.ReflectRuntimeException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.jiezhou.api.ICache;
import org.jiezhou.core.support.proxy.ICacheProxy;
import org.jiezhou.core.support.proxy.bs.CacheProxyBs;
import org.jiezhou.core.support.proxy.bs.CacheProxyBsContext;

import java.lang.reflect.Method;

/**
 * @author: jiezhou
 **/

public class CglibProxy implements MethodInterceptor, ICacheProxy {
    /**
     * 被代理对象
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */

    private final ICache target;

    public CglibProxy(ICache target) {
        this.target = target;
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        CacheProxyBsContext params = CacheProxyBsContext.newInstance()
                .target(target)
                .method(method)
                .params(objects);
        return CacheProxyBs.newInstance()
                .context(params)
                .execute();
    }

    @Override
    public Object proxy() {
        Enhancer enhancer = new Enhancer();
        // 目标对象类
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        // 通过字节码技术动态创建子类实例
        return enhancer.create();
    }
}
