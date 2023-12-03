package org.jiezhou.core.support.proxy.dynamic;

import org.jiezhou.api.ICache;
import org.jiezhou.core.support.proxy.ICacheProxy;
import org.jiezhou.core.support.proxy.bs.CacheProxyBs;
import org.jiezhou.core.support.proxy.bs.CacheProxyBsContext;
import org.jiezhou.core.support.proxy.bs.ICacheProxyBsContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletionService;

/**
 * @author: jiezhou
 * 动态代理
 *
 *  * 1. 对于 executor 的抽象，使用 {@link CompletionService}
 *  * 2. 确保唯一初始化 executor，在任务执行的最后关闭 executor。
 *  * 3. 异步执行结果的获取，异常信息的获取。
 **/

public class DynamicProxy implements InvocationHandler, ICacheProxy {
    /**
     * 被代理的对象
     */
    private final ICache target;

    public DynamicProxy(ICache target) {
        this.target = target;
    }

    /**
     * 这中方式虽然实现了异步，但是存在一个缺陷
     * 强制用户返回值为future的子类
     * <p>
     * 如何实现不影响原来的值
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ICacheProxyBsContext context = CacheProxyBsContext.newInstance()
                .method(method)
                .params(args)
                .target(target);
        return CacheProxyBs.newInstance().context(context).execute();
    }

    @Override
    public Object proxy() {
        // 通过 Proxy.newProxyInstance 创建代理对象
        InvocationHandler handler = new DynamicProxy(target);
        return Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                handler);
    }
}
