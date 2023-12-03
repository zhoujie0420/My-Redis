package org.jiezhou.core.support.listener.slow;

import cn.hutool.log.Log;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheSlowListener;
import org.jiezhou.api.ICacheSlowListenerContext;

/**
 * 慢日志监听类
 */
public class CacheSlowListener implements ICacheSlowListener {

    private static final Log log = Log.get();
    @Override
    public void listen(ICacheSlowListenerContext context) {
        log.warn("CacheSlowListener listen");
    }

    @Override
    public long slowerThanMills() {
        return 1000L;
    }
}
