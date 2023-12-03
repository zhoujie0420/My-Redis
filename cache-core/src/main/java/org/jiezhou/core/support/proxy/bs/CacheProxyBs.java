package org.jiezhou.core.support.proxy.bs;

import org.jiezhou.annotation.CacheInterceptor;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheInterceptor;
import org.jiezhou.api.ICachePersist;
import org.jiezhou.core.support.interceptor.CacheInterceptorContext;
import org.jiezhou.core.support.interceptor.CacheInterceptors;
import org.jiezhou.core.support.persist.CachePersistAof;

import java.util.List;

/**
 * @author: jiezhou
 * 代理引导类
 **/

public final class CacheProxyBs {
    private CacheProxyBs() {
    }

    private ICacheProxyBsContext context;
    /**
     * 默认通用拦截器
     *
     * JDK 的泛型擦除导致这里不能使用泛型
     */
    @SuppressWarnings("all")
    private final List<ICacheInterceptor> commonInterceptors = CacheInterceptors.defaultCommonList();

    /**
     * 默认刷新拦截器
     */
    @SuppressWarnings("all")
    private final List<ICacheInterceptor> refreshInterceptors = CacheInterceptors.defaultRefreshList();

    /**
     * 持久化拦截器
     */
    @SuppressWarnings("all")
    private final ICacheInterceptor persistenceInterceptor = CacheInterceptors.aof();

    /**
     * 驱除拦截器
     */
    @SuppressWarnings("all")
    private final ICacheInterceptor evictInterceptor = CacheInterceptors.evict();
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
        //1. 开始的时间
        final long startMills = System.currentTimeMillis();
        final ICache cache = context.target();
        CacheInterceptorContext interceptorContext = CacheInterceptorContext.newInstance()
                .startTime(startMills)
                .method(context.method())
                .params(context.params())
                .cache(context.target())
                ;

        //1. 获取刷新注解信息
        CacheInterceptor cacheInterceptor = context.interceptor();
        this.interceptorHandler(cacheInterceptor, interceptorContext, cache, true);

        //2. 执行方法
        Object result = context.process();

        final long endMills = System.currentTimeMillis();
        interceptorContext.endTime(endMills).result(result);

        // 方法执行完成
        this.interceptorHandler(cacheInterceptor, interceptorContext, cache, false);
        return result;
    }

    /**
     * 拦截器执行类
     * @param cacheInterceptor 缓存拦截器
     * @param interceptorContext 上下文
     * @param cache 缓存
     * @param before 是否执行执行
     */
    @SuppressWarnings("all")
    private void interceptorHandler(CacheInterceptor cacheInterceptor,
                                    CacheInterceptorContext interceptorContext,
                                    ICache cache,
                                    boolean before) {
        if(cacheInterceptor != null) {
            //1. 通用
            if(cacheInterceptor.common()) {
                for(ICacheInterceptor interceptor : commonInterceptors) {
                    if(before) {
                        interceptor.before(interceptorContext);
                    } else {
                        interceptor.after(interceptorContext);
                    }
                }
            }

            //2. 刷新
            if(cacheInterceptor.refresh()) {
                for(ICacheInterceptor interceptor : refreshInterceptors) {
                    if(before) {
                        interceptor.before(interceptorContext);
                    } else {
                        interceptor.after(interceptorContext);
                    }
                }
            }

            //3. 持久化
           final ICachePersist cachePersist = cache.persist();
            if(cacheInterceptor.aof() && (cachePersist instanceof CachePersistAof)){
                if(before) {
                    persistenceInterceptor.before(interceptorContext);
                } else {
                    persistenceInterceptor.after(interceptorContext);
                }
            }

            //4. 驱除
            if(cacheInterceptor.evict()){
                if(before) {
                    evictInterceptor.before(interceptorContext);
                } else {
                    evictInterceptor.after(interceptorContext);
                }
            }
        }
    }

}
