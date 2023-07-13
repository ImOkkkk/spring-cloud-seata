package com.imokkkk.order.discovery.client.loadbalancer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuwy
 * @date 2023-07-13 13:35
 * @since 1.0
 */
public class CpuUsageLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers;

    public CpuUsageLoadBalancer(
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers) {
        this.serviceInstanceListSuppliers = serviceInstanceListSuppliers;
    }

    // 根据CPU使用率负载均衡
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier serviceInstanceListSupplier =
                serviceInstanceListSuppliers.getIfAvailable();
        Flux<List<ServiceInstance>> flux = serviceInstanceListSupplier.get();
        List<ServiceInstance> serviceInstances = flux.blockFirst();
        List<ServiceInstanceWrapper> serviceInstanceWrappers =
                serviceInstances.stream()
                        .map(
                                s -> {
                                    return new ServiceInstanceWrapper(
                                            Integer.valueOf(s.getMetadata().get("cpu-usage")), s);
                                })
                        .sorted()
                        .collect(Collectors.toList());
        return Mono.justOrEmpty(
                new DefaultResponse(serviceInstanceWrappers.get(0).getServiceInstance()));
    }

    class ServiceInstanceWrapper implements Comparable<ServiceInstanceWrapper> {
        private Integer cpuUsage;
        private ServiceInstance serviceInstance;

        public ServiceInstanceWrapper(Integer cpuUsage, ServiceInstance serviceInstance) {
            this.cpuUsage = cpuUsage;
            this.serviceInstance = serviceInstance;
        }

        public Integer getCpuUsage() {
            return cpuUsage;
        }

        public ServiceInstance getServiceInstance() {
            return serviceInstance;
        }

        @Override
        public int compareTo(ServiceInstanceWrapper o) {
            if (o == this) {
                return 0;
            }
            return Double.compare(this.cpuUsage, o.cpuUsage);
        }
    }
}
