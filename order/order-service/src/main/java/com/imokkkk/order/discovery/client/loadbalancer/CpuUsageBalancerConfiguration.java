package com.imokkkk.order.discovery.client.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author liuwy
 * @date 2023-07-13 13:46
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
public class CpuUsageBalancerConfiguration {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> cpuUsageLoadBalancer(
            Environment environment, LoadBalancerClientFactory factory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new CpuUsageLoadBalancer(
                factory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }
}
