package org.drod.cloud.web;

import org.drod.framework.web.exception.BizExceptionResolver;
import org.drod.framework.web.exception.GlobalExceptionResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 异常处理器
 *
 * @author drod
 */
@Configuration
@ComponentScan("org.drod.cloud.web")
public class ExceptionAdviceConfiguration {
    public ExceptionAdviceConfiguration() {}

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionResolver bizExceptionResolver() {
        return new BizExceptionResolver();
    }
}
