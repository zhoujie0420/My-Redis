package org.jiezhou.core.support.interceptor.common;


import cn.hutool.log.Log;
import com.github.houbb.heaven.util.util.CollectionUtil;
import org.jiezhou.api.ICacheInterceptor;
import org.jiezhou.api.ICacheInterceptorContext;
import org.jiezhou.api.ICacheSlowListener;
import org.jiezhou.core.support.listener.slow.CacheSlowListenerContext;

import java.util.List;

/**
 * @author: jiezhou
 * 耗时统计
 **/

public class CacheInterceptorCost<K, V> implements ICacheInterceptor<K, V> {
    private static final Log log = Log.get();

    @Override
    public void before(ICacheInterceptorContext<K, V> context) {
        log.debug("Cost start, method: {}", context.method().getName());

    }

    @Override
    public void after(ICacheInterceptorContext<K, V> context) {
        long cost = context.endTime() - context.startTime();
        String name = context.method().getName();
        log.debug("Cost end, method: {}, cost: {}ms", name, cost);

        //添加慢日志操作
        List<ICacheSlowListener> slowListeners = context.cache().slowListeners();
        if(CollectionUtil.isEmpty(slowListeners)){
            return;
        }

        CacheSlowListenerContext listenerContext = CacheSlowListenerContext.newInstance()
                .startTimeMills(context.startTime())
                .endTimeMills(context.endTime())
                .costTimeMills(cost)
                .methodName(name)
                .params(context.params())
                .result(context.result());

        // 设置多个，考虑不同的慢日志级别
        for(ICacheSlowListener slowListener : slowListeners) {
            long slowThanMills = slowListener.slowerThanMills();
            if(cost >= slowThanMills) {
                slowListener.listen(listenerContext);
            }
        }
    }
}
