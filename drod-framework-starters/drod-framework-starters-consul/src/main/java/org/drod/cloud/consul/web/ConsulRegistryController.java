package org.drod.cloud.consul.web;

import org.drod.cloud.consul.registration.ConsulRegisterRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/consul/manual"})
public class ConsulRegistryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsulRegistryController.class);

    @Autowired
    private ConsulRegisterRegistration consulRegisterRegistration;

    @GetMapping({"/register"})
    public String register() {
        try {
            this.consulRegisterRegistration.register();
            return "OK";
        } catch (Exception exception) {
            LOGGER.error("manual register consul failure , ", exception);
            return "FAIL";
        }
    }

    @GetMapping({"/deregister"})
    public String deregister() {
        try {
            this.consulRegisterRegistration.deRegister();
            return "OK";
        } catch (Exception exception) {
            LOGGER.error("manual deregister consul failure , ", exception);
            return "FAIL";
        }
    }
}
