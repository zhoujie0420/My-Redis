package org.jiezhou.core.support.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiezhou
 **/

public final class Maps {
    private Maps() {
    }

    /**
     * hashmap 实现策略
     */
    public static <K, V> Map<K, V> hashMap() {
        return new HashMap<>();
    }
}
