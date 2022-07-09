package org.drod.cloud.consul.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ConsulRegistryAutoConfiguration.class})
@ComponentScan(basePackages = {"org.drod.cloud.consul.web"})
public class ConsulWebAutoConfiguration {
    public ConsulWebAutoConfiguration() {}
}
