package org.jiezhou.api;

public interface ICacheRemoveListener <K,V> {
    /**
     * 监听
     */
    void listen(final ICacheRemoveListenerContext<K,V> context);
}
