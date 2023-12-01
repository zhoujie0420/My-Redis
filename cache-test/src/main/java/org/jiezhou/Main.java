package org.jiezhou;


import org.jiezhou.api.ICache;
import org.jiezhou.core.bs.CacheBs;
import org.jiezhou.core.support.evict.CacheEvicts;
import org.jiezhou.core.support.map.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class Main {
    @Test
    public void test() {
        ICache<String, String> cache = CacheBs.<String, String>newInstance()
                .size(2)
                .build();

        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");

        Assert.assertEquals(2, cache.size());
        System.out.println(cache.keySet());
    }

    /**
     * 配置指定测试
     */
    @Test
    public void test2() {
        ICache<String, String> cache = CacheBs.<String, String>newInstance()
                .map(Maps.<String, String>hashMap())
                .evict(CacheEvicts.<String, String>fifo())
                .size(2)
                .build();

        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");

        Assert.assertEquals(2, cache.size());
        System.out.println(cache.keySet());
    }
    /**
     * 过期测试
     * @since 0.0.3
     */
    @Test
    public void expireTest() throws InterruptedException {
        ICache<String, String> cache = CacheBs.<String,String>newInstance()
                .size(3)
                .build();

        cache.put("1", "1");
        cache.put("2", "2");

        cache.expire("1", 40);
        Assert.assertEquals(2, cache.size());

        TimeUnit.MILLISECONDS.sleep(50);
        Assert.assertEquals(1, cache.size());
        System.out.println(cache.keySet());
    }

}