package org.jiezhou.core.support.listener.remove;

import org.jiezhou.api.ICacheRemoveListener;

import java.util.ArrayList;
import java.util.List;

public class CacheRemoveListeners {
    private CacheRemoveListeners() {
    }

    /**
     * 默认监听类
     */
    @SuppressWarnings("all")
    public static <K,V>List<ICacheRemoveListener<K,V>> defaults(){
        ArrayList<ICacheRemoveListener<K,V>> listeners = new ArrayList<>();
        listeners.add(new CacheRemoveListener());
        return listeners;
    }
}
