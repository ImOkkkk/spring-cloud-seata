package com.imokkkk.order.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author liuwy
 * @date 2023-10-23 10:26
 * @since 1.0
 */
public class LoggingInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @RuntimeType
    public Object doLog(@Origin Method method, @SuperCall Callable<?> callable) {
        logger.info("before invoke ...");
        long start = System.currentTimeMillis();
        try {
            logger.info("ByteBuddy enhance ...");
            return callable.call();
        } catch (Exception e) {
            logger.error("error occupied", e);
            throw new RuntimeException(e);
        } finally {
            System.out.println(
                    method + "invoke finished, it took " + (System.currentTimeMillis() - start));
        }
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Log {}
}
