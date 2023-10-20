package com.imokkkk.order.micrometer;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author liuwy
 * @date 2023-10-13 16:42
 * @since 1.0
 */
@Component
public class HeapMemoryMetrics
        implements MeterBinder, EnvironmentAware, InitializingBean, BeanFactoryAware {
    private String applicationName;
    private String profile;
    private String instanceId;
    private MeterRegistry meterRegistry;
    private Gauge heapMemoryGauge;
    private MemoryMXBean memoryMXBean;

    private Tags getTags() {
        return Tags.of(
                Tag.of("applicationName", applicationName),
                Tag.of("profile", profile),
                Tag.of("instanceId", instanceId));
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        //Gauge：动态的指标类型，即其值会根据监测到的变化而被动态更新
        this.heapMemoryGauge =
                Gauge.builder(
                                "application.heap.memory",
                                this.memoryMXBean,
                                mBean -> {
                                    MemoryUsage heapMemoryUsage = mBean.getHeapMemoryUsage();
                                    long max = heapMemoryUsage.getMax();
                                    long used = heapMemoryUsage.getUsed();
                                    BigDecimal usage = new BigDecimal(used);
                                    return usage.divide(new BigDecimal(max), 3, RoundingMode.UP)
                                            .doubleValue();
                                })
                        .tags(getTags())
                        .strongReference(true)
                        .register(this.meterRegistry);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Registration registration = beanFactory.getBean(Registration.class);
        this.instanceId = registration.getInstanceId();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    @Override
    public void setEnvironment(Environment environment) {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            this.profile = "default";
        } else {
            this.profile = activeProfiles[0];
        }
        this.applicationName = environment.getProperty("spring.application.name");
    }
}
