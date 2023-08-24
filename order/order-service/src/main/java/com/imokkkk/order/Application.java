package com.imokkkk.order;

import com.imokkkk.order.discovery.client.loadbalancer.CpuUsageBalancerConfiguration;
import com.imokkkk.order.micrometer.FeignCallCounterMetrics;
import com.imokkkk.order.micrometer.config.DefaultFeignClientsConfiguration;
import com.imokkkk.order.micrometer.config.MicrometerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:49
 * @since 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = DefaultFeignClientsConfiguration.class)
@MapperScan(basePackages = "com.imokkkk.order.mapper")
@EnableEurekaClient
@LoadBalancerClient(name = "storage", configuration = CpuUsageBalancerConfiguration.class)
@Import({MicrometerConfiguration.class, FeignCallCounterMetrics.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
