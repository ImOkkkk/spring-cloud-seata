package com.imokkkk.gateway.config;

import com.imokkkk.gateway.filter.HttpRequestCounterGatewayFilterFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.gateway.config.conditional.ConditionalOnEnabledFilter;
import org.springframework.context.annotation.Bean;

/**
 * @author liuwy
 * @date 2023-11-07 14:07
 * @since 1.0
 */
@ConditionalOnClass(MeterRegistry.class)
public class MetricsGatewayAutoConfiguration {

    @Bean
    @ConditionalOnEnabledFilter
    public HttpRequestCounterGatewayFilterFactory httpStatusCounterGatewayFilterFactory() {
        return new HttpRequestCounterGatewayFilterFactory();
    }

    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }
}
