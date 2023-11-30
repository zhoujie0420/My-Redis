package org.jiezhou.annotation;

import jdk.jfr.Relational;

import java.lang.annotation.*;

/**
 * @author: jiezhou
 * 缓存拦截器
 **/

@Documented
@Inherited   //可以使得该注解在子类中继承。
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheInterceptor {

    /**
     * 通用拦截器
     *
     * 1.耗时统计
     * 2.慢日志统计
     *
     * 默认开启
     */
    boolean common() default true;

    /**
     * 是否启用刷新
     *
     */
    boolean refresh() default false;

    /**
     * 操作是否需要append to file 默认false
     * aof 日志追加机制
     */
    boolean aof() default false;

    /**
     * 是否执行驱除更新
     * 主要用于 LRU /LFU 等算法
     */
    boolean evict() default false;

}
