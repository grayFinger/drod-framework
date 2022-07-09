package org.drod.cloud.sms.config;

import org.drod.cloud.sms.api.SmsSendApi;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitScan;
import org.drod.cloud.sms.api.TemplateApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注入 bean
 *
 * @author Mist
 * @create: 2022-06-10 15:37
 **/
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(value = "drod.submail.sms.enabled", havingValue = "true")
@RetrofitScan("org.drod.cloud.sms")
public class SmsConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsConfiguration.class);

    @Bean
    public SmsSendApi initSMSController(SmsProperties smsProperties) {
        LOGGER.info(">>>>>>>>>>> SMSController init.");
        return new SmsSendApi(smsProperties);
    }


    @Bean
    public TemplateApi initTemplateApiController(SmsProperties smsProperties) {
        LOGGER.info(">>>>>>>>>>> initTemplateApiController init.");
        return new TemplateApi(smsProperties);
    }
}
