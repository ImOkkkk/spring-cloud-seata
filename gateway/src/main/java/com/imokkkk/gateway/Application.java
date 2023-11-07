package com.imokkkk.gateway;

import com.imokkkk.gateway.config.MetricsGatewayAutoConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

/**
 * @author ImOkkkk
 * @date 2023/1/3 15:42
 * @since 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@Import({MetricsGatewayAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
