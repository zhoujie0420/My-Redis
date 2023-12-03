package org.jiezhou.core.support.interceptor;

import org.jiezhou.api.ICacheInterceptor;
import org.jiezhou.core.support.interceptor.aof.CacheInterceptorAof;
import org.jiezhou.core.support.interceptor.common.CacheInterceptorCost;
import org.jiezhou.core.support.interceptor.evict.CacheInterceptorEvict;
import org.jiezhou.core.support.interceptor.refresh.CacheInterceptorRefresh;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiezhou
 **/

public final class CacheInterceptors {
    /**
     * 默认通用
     */
    @SuppressWarnings("all")
    public static List<ICacheInterceptor> defaultCommonList(){
        ArrayList<ICacheInterceptor> list = new ArrayList<>();
        list.add(new CacheInterceptorCost());
        return list;
    }

    @SuppressWarnings("all")
    public static List<ICacheInterceptor> defaultRefreshList(){
        ArrayList<ICacheInterceptor> list = new ArrayList<>();
        list.add(new CacheInterceptorRefresh());
        return list;
    }


    /**
     * AOF 模式
     * @return 结果
     */
    @SuppressWarnings("all")
    public static ICacheInterceptor aof() {
        return new CacheInterceptorAof();
    }

    /**
     * 驱除策略拦截器
     * @return 结果
     */
    @SuppressWarnings("all")
    public static ICacheInterceptor evict() {
        return new CacheInterceptorEvict();
    }

}
