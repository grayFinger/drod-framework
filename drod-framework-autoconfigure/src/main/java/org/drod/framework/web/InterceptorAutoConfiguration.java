package org.drod.framework.web;

import org.drod.framework.web.interceptor.WebContextInterceptor;
import org.drod.framework.web.interceptor.WebInterceptorConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配
 *
 * @author yangx
 */
@Configuration
public class InterceptorAutoConfiguration {

    @Bean
    public WebContextInterceptor webContextInterceptor(@Value("${spring.application.name}") String app) {
        return new WebContextInterceptor(app);
    }

    @Bean
    public WebInterceptorConfigurer webInterceptorConfigurer(WebContextInterceptor webContextInterceptor) {
        return new WebInterceptorConfigurer(webContextInterceptor);
    }

}
