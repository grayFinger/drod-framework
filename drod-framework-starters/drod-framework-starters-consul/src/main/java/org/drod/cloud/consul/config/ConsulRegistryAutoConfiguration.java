package org.drod.cloud.consul.config;


import org.drod.cloud.consul.registration.ConsulRegisterRegistration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter({ConsulAutoServiceRegistrationAutoConfiguration.class, ConsulServiceRegistryAutoConfiguration.class})
public class ConsulRegistryAutoConfiguration {
    public ConsulRegistryAutoConfiguration() {
    }

    @Bean
    ConsulRegisterRegistration consulRegisterRegistration(ConsulDiscoveryProperties consulDiscoveryProperties, ConsulAutoRegistration registration, ConsulServiceRegistry serviceRegistry) {
        return new ConsulRegisterRegistration(consulDiscoveryProperties, registration, serviceRegistry);
    }
}
