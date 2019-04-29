package com.synpore.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributeLock {
    /**
     * 锁key前缀
     *
     * @return key前缀
     */
    String pre();

    /**
     * 锁key
     *
     * @return key
     */
    String key();

    /**
     * 过期时间默认30秒
     * @return
     */
    long expire() default 30L;
}
