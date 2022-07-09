package org.drod.framework.consul;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 修改consul属性配置入口
 * @author e9
 */
@Configuration
@ConditionalOnClass(ConsulRegistrationCustomizer.class)
public class ConsulConfiguration {

    @Bean
    public TagsModifyRegistrationCustomizer tagsModifyRegistrationCustomizer() {
        return new TagsModifyRegistrationCustomizer();
    }
}
