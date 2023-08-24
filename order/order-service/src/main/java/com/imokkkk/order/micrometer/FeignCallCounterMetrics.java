package com.imokkkk.order.micrometer;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

/**
 * @author liuwy
 * @date 2023-08-24 16:38
 * @since 1.0
 */
public class FeignCallCounterMetrics implements RequestInterceptor, MeterBinder {
    private static MeterRegistry meterRegistry;

    private static Counter totalCounter;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 方法统计
        String feignMethod = requestTemplate.methodMetadata().configKey();
        Counter counter =
                Counter.builder("feign.calls").tags("method", feignMethod).register(meterRegistry);
        counter.increment();
        // 全局统计
        totalCounter.increment();
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.totalCounter = Counter.builder("feign.total-calls").register(meterRegistry);
    }
}
