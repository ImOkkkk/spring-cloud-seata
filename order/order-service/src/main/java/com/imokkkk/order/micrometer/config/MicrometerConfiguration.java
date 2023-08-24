package com.imokkkk.order.micrometer.config;

import static java.util.Arrays.asList;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cloud.client.serviceregistry.Registration;

/**
 * @author liuwy
 * @date 2023-08-24 16:57
 * @since 1.0
 */
public class MicrometerConfiguration implements MeterRegistryCustomizer {

    @Value("${spring.application.name:default}")
    private String applicationName;

    @Autowired private Registration registration;

    /** 应用部署环境：TEST（测试）、STAGING（预发）、PROD（生产） */
    @Value("${env:TEST}")
    private String env;

    @Override
    public void customize(MeterRegistry registry) {
        registry.config()
                .commonTags(
                        asList(
                                Tag.of("application", applicationName), // 应用维度的 Tag
                                Tag.of("host", registration.getHost()), // 应用 Host 的 Tag
                                Tag.of("env", env) // 应用部署环境
                                ));
    }
}
