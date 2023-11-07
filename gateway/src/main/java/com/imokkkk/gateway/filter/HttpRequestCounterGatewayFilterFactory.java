package com.imokkkk.gateway.filter;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * @author liuwy
 * @date 2023-11-07 14:07
 * @since 1.0
 */
public class HttpRequestCounterGatewayFilterFactory
        extends AbstractGatewayFilterFactory<
                HttpRequestCounterGatewayFilter.HttpRequestCounterConfig>
        implements MeterBinder {

    private MeterRegistry meterRegistry;

    @Override
    public void bindTo(MeterRegistry registry) {
        this.meterRegistry = registry;
    }

    public HttpRequestCounterGatewayFilterFactory() {
        super(HttpRequestCounterGatewayFilter.HttpRequestCounterConfig.class);
    }

    @Override
    public GatewayFilter apply(HttpRequestCounterGatewayFilter.HttpRequestCounterConfig config) {
        return new HttpRequestCounterGatewayFilter(config, this.meterRegistry);
    }
}
