package org.drod.cloud.email.config;


import org.drod.cloud.email.api.MailApi;
import org.drod.cloud.email.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;

/**
 * 注入 bean
 *
 * @author Deng 2022-07-01
 **/
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({MimeMessage.class, MimeType.class, MailSender.class})
@ConditionalOnMissingBean(MailSender.class)
@EnableConfigurationProperties(DrodMailProperties.class)
@Import({DrodMailSenderPropertiesConfiguration.class})
public class DrodMailConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrodMailConfiguration.class);

    static class MailSenderCondition extends AnyNestedCondition {

        MailSenderCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }
        @ConditionalOnProperty(prefix = "drod.mail", name = "host")
        static class HostProperty {

        }
    }

    @Bean
    @ConditionalOnBean(JavaMailSender.class)
    public MailService initMailServiceController(DrodMailProperties drodMailProperties) {
        LOGGER.info(">>>>>>>>>>> MailConfiguration init.");
        return new MailService(drodMailProperties);
    }

    @Bean
    public MailApi initMailApiController(MailService mailService) {
        LOGGER.info(">>>>>>>>>>> MailApi init.");
        return new MailApi(mailService);
    }
}
