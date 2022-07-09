package org.drod.framework.registry;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitorAutoConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.cloud.consul.discovery.instance-id}")
    private String instanceId;

    @Bean
    MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
        return (registry) -> {
            registry.config().commonTags("application", applicationName, "instanceId", instanceId);
        };
    }

}
