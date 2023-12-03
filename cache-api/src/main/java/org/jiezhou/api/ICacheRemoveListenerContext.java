package org.jiezhou.api;

/**
 * 清除监听器上下文
 * @param <K>
 * @param <V>
 */
public interface ICacheRemoveListenerContext <K,V>{
    /**
     * 清空 key
     */
    K key();

    /**
     * 值
     */
    V value();

    /**
     * 删除类型
     */
    String type();
}
