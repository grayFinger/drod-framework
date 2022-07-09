package org.drod.cloud.consul.registration;

import org.drod.cloud.consul.web.ConsulRegistryController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;

import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsulRegisterRegistration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsulRegistryController.class);

    private ConsulDiscoveryProperties consulDiscoveryProperties;
    private AtomicBoolean running = new AtomicBoolean(false);
    private ConsulAutoRegistration registration;
    private ConsulServiceRegistry serviceRegistry;

    public ConsulRegisterRegistration(ConsulDiscoveryProperties consulDiscoveryProperties,
        ConsulAutoRegistration registration, ConsulServiceRegistry serviceRegistry) {
        this.registration = registration;
        this.serviceRegistry = serviceRegistry;
        this.consulDiscoveryProperties = consulDiscoveryProperties;
    }

    public void register() {
        initRunning();
        if (this.running.compareAndSet(false, true)) {
            this.consulDiscoveryProperties.setRegister(true);
            this.consulDiscoveryProperties.setDeregister(false);
            this.serviceRegistry.register(this.registration);
            LOGGER.info("end register service {}", this.registration.getService());
        }
    }

    @PreDestroy
    public void deRegister() {
        initRunning();
        if (this.running.compareAndSet(true, false)) {
            this.consulDiscoveryProperties.setDeregister(true);
            this.consulDiscoveryProperties.setRegister(false);
            this.serviceRegistry.deregister(this.registration);
            LOGGER.info("end deRegister service {}", this.registration.getService());
        }
    }

    private void initRunning() {
        if (this.consulDiscoveryProperties.isRegister()) {
            this.running.compareAndSet(false, true);
            this.consulDiscoveryProperties.setRegister(true);
            this.consulDiscoveryProperties.setDeregister(false);
        } else {
            this.running.compareAndSet(true, false);
            this.consulDiscoveryProperties.setDeregister(true);
            this.consulDiscoveryProperties.setRegister(false);

        }
    }

}
