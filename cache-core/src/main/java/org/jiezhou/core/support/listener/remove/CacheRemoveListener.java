package org.jiezhou.core.support.listener.remove;

import cn.hutool.log.Log;
import org.jiezhou.api.ICacheRemoveListener;
import org.jiezhou.api.ICacheRemoveListenerContext;

import java.security.PrivateKey;

public class CacheRemoveListener<K,V> implements ICacheRemoveListener<K,V> {

    private static final Log log = Log.get();



    @Override
    public void listen(ICacheRemoveListenerContext<K, V> context) {
        log.debug("CacheRemoveListener listen");
    }


}
