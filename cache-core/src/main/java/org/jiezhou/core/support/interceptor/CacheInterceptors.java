package org.jiezhou.core.support.interceptor;

import org.jiezhou.api.ICacheInterceptor;
import org.jiezhou.core.support.common.CacheInterceptorCost;
import org.jiezhou.core.support.refresh.CacheInterceptorRefresh;

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
}
