package com.imokkkk.order.micrometer.config;

import com.imokkkk.order.micrometer.FeignCallCounterMetrics;

import org.springframework.cloud.openfeign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 默认 FeignClients 配置（扩展 Spring Cloud FeignClientsConfiguration）
 *
 * @author liuwy
 * @date 2023-08-24 16:37
 * @since 1.0
 */
@Import({FeignClientConfiguration.class})
public class DefaultFeignClientsConfiguration {
    @Bean
    public FeignCallCounterMetrics feignCallCounterMetrics() {
        return new FeignCallCounterMetrics();
    }
}
