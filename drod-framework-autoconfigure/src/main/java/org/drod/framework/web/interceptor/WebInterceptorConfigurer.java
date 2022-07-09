package org.drod.framework.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 *
 * @author e9
 */
public class WebInterceptorConfigurer implements WebMvcConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebInterceptorConfigurer.class);


    private WebContextInterceptor webContextInterceptor;

    public WebInterceptorConfigurer(WebContextInterceptor webContextInterceptor) {
        this.webContextInterceptor = webContextInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOGGER.info("add request params interceptor...");
        registry.addInterceptor(webContextInterceptor).addPathPatterns("/**");
    }
}
