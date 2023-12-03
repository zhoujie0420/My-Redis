package org.jiezhou.core.support.persist;

import cn.hutool.log.Log;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICachePersist;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


/**
 * 内部缓存持久化
 *
 * @param <k>
 * @param <V>
 */
public class InnerCachePersist<k, V> {

    private static final Log log = Log.get();

    /**
     * 缓存信息
     */
    private final ICache<k, V> cache;

    /**
     * 缓存持久化策略
     */
    private final ICachePersist<k, V> persist;

    /**
     * 现场执行类
     */
    private final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public InnerCachePersist(ICache<k, V> cache, ICachePersist<k, V> persist) {
        this.cache = cache;
        this.persist = persist;

        //初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleWithFixedDelay(() -> {
            try {
                log.info("开始持久化缓存信息");
                persist.persist(cache);
                log.info("完成持久化缓存信息");
            } catch (Exception exception) {
                log.error("文件持久化异常", exception);
            }
        }, persist.delay(), persist.period(), persist.timeUnit());
    }
}
