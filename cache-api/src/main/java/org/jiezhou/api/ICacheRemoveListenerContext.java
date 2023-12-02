package org.jiezhou.api;

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
