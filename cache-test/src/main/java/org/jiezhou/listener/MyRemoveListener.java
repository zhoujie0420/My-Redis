package org.jiezhou.listener;

import org.jiezhou.api.ICacheRemoveListener;
import org.jiezhou.api.ICacheRemoveListenerContext;

public class MyRemoveListener<K,V> implements ICacheRemoveListener<K, V> {

    @Override
    public void listen(ICacheRemoveListenerContext<K, V> context) {
        System.out.println("MyRemoveListener listen");
    }
}
