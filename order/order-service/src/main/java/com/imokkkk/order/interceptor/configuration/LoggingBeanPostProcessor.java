package com.imokkkk.order.interceptor.configuration;

import com.imokkkk.order.interceptor.LoggingInterceptor;
import com.imokkkk.order.interceptor.LoggingInterceptor.Log;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuwy
 * @date 2023-10-23 10:50
 * @since 1.0
 */
@Configuration
public class LoggingBeanPostProcessor
        extends AbstractAnnotationByteBuddyBeanPostProcessor<LoggingInterceptor.Log> {

    private final LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

    @Override
    protected Unloaded<?> doIntercept(Class beanType) {
        return new ByteBuddy()
                .subclass(beanType)
                .method(ElementMatchers.isAnnotatedWith(LoggingInterceptor.Log.class))
                .intercept(MethodDelegation.to(loggingInterceptor))
                .make();
    }

    @Override
    protected Class<Log> getAnnotationClass() {
        return LoggingInterceptor.Log.class;
    }
}
