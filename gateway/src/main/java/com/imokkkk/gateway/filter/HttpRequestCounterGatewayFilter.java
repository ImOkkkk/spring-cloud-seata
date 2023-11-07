package com.imokkkk.gateway.filter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liuwy
 * @date 2023-11-07 13:58
 * @since 1.0
 */
public class HttpRequestCounterGatewayFilter implements GatewayFilter {

    private final HttpRequestCounterConfig counterConfig;

    private final MeterRegistry registry;

    public HttpRequestCounterGatewayFilter(
            HttpRequestCounterConfig counterConfig, MeterRegistry registry) {
        Objects.requireNonNull(counterConfig);
        this.counterConfig = counterConfig;
        this.registry = registry;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String method = exchange.getRequest().getMethod().name();

        if (!this.counterConfig.isIgnored(method)) {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            String routeId = route.getId();
            final Counter counter =
                    Counter.builder(String.format("COUNTER.gateway.%s", routeId))
                            .tag("source.ip", exchange.getRequest().getURI().getHost())
                            .register(this.registry);

            counter.increment();
        }

        return chain.filter(exchange);
    }

    public static class HttpRequestCounterConfig {

        private List<HttpMethod> ignoredMethod = new ArrayList<>();

        public HttpRequestCounterConfig() {
            this.ignoredMethod.add(HttpMethod.OPTIONS);
        }

        public List<HttpMethod> getIgnoredMethod() {
            return ignoredMethod;
        }

        public void setIgnoredMethod(List<HttpMethod> ignoredMethod) {
            this.ignoredMethod = ignoredMethod;
        }

        public boolean isIgnored(String mehtod) {
            for (HttpMethod httpMethod : ignoredMethod) {
                if (httpMethod.matches(mehtod)) return true;
            }
            return false;
        }
    }
}
