package org.jiezhou.core.support.interceptor.aof;


import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheInterceptor;
import org.jiezhou.api.ICacheInterceptorContext;
import org.jiezhou.api.ICachePersist;
import org.jiezhou.core.model.PersistAofEntry;
import org.jiezhou.core.support.persist.CachePersistAof;

/**
 * 顺序追加模式
 *
 * AOF持久化文件，不考虑buffer
 */
public class CacheInterceptorAof <K,V> implements ICacheInterceptor<K,V> {

    private static final Log log = Log.get();
    @Override
    public void before(ICacheInterceptorContext<K, V> context) {

    }

    @Override
    public void after(ICacheInterceptorContext<K, V> context) {
        //持久化
        ICache<K, V> cache = context.cache();
        ICachePersist<K, V> persist = cache.persist();

        if(persist instanceof CachePersistAof){
            CachePersistAof<K,V> cachePersistAof = (CachePersistAof<K, V>) persist;

            String name = context.method().getName();
            PersistAofEntry aofEntry  = PersistAofEntry.newInstance();
            aofEntry.setMethodName(name);
            aofEntry.setParams(context.params());

            String jsonStr = JSONUtil.toJsonStr(aofEntry);

            // 直接持久化
            log.debug("Aof append: {}", jsonStr);
            cachePersistAof.append(jsonStr);
        }

    }
}
