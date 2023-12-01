package org.jiezhou.core.support.proxy;

import com.github.houbb.heaven.support.proxy.none.NoneProxy;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import org.jiezhou.api.ICache;
import org.jiezhou.core.support.proxy.cglib.CglibProxy;
import org.jiezhou.core.support.proxy.dynamic.DynamicProxy;

import java.lang.reflect.Proxy;

/**
 * @author: jiezhou
 **/

public final class CacheProxy {
    private CacheProxy() {
    }

    /**
     * 获取代理对象
     */
    @SuppressWarnings("all")
    public static <K, V> ICache<K, V> getProxy(final ICache<K, V> cache) {
        if (ObjectUtil.isNull(cache)) {
            return (ICache<K, V>) new NoneProxy(cache).proxy();
        }

        final Class clazz = cache.getClass();

        // 如果targetClass 本身是个接口或者是jdk proxy 生成 则使用jdk动态代理
        // 参考spring AOP判断
        if (clazz.isInterface() || Proxy.isProxyClass(clazz)) {
            return (ICache<K, V>) new DynamicProxy(cache).proxy();
        }
        return (ICache<K, V>) new CglibProxy(cache).proxy();
    }
}
