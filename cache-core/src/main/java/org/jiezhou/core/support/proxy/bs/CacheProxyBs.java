package org.jiezhou.core.support.proxy.bs;

import org.jiezhou.annotation.Refresh;
import org.jiezhou.api.ICache;
import org.jiezhou.core.support.proxy.ICacheProxy;

/**
 * @author: jiezhou
 * 代理引导类
 **/

public final class CacheProxyBs {
    private CacheProxyBs() {
    }

    private ICacheProxyBsContext context;

    /**
     * 新建对象实例
     */
    public static CacheProxyBs newInstance() {
        return new CacheProxyBs();
    }

    public CacheProxyBs context(ICacheProxyBsContext context) {
        this.context = context;
        return this;
    }

    /**
     * 执行
     */
    @SuppressWarnings("all")
    public Object execute()  throws Throwable {
        // 基本信息
        final ICache cache = context.target();


        //1. 获取刷新注解信息
        Refresh refresh = context.refresh();
        if(refresh != null) {
            cache.cacheExpire().refreshExpire(cache.keySet());
        }

        //2. 正常执行
        return context.process();
    }


}
