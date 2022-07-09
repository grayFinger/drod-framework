package org.drod.framework.webflux.config;

import org.drod.framework.webflux.exception.WebFluxErrorExceptionHandler;
import org.drod.framework.webflux.filter.InitRequestContextWebFilter;
import org.drod.framework.webflux.filter.TraceWebFilter;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;

/**
 * 响应式
 *
 * @author e9
 */
@Configuration
public class WebFilterAutoConfiguration {

    /**
     * 初始化请求上下文web过滤器
     *
     * @return
     */
    @Bean
    @Order(-1)
    public InitRequestContextWebFilter initRequestContextWebFilter() {
        return new InitRequestContextWebFilter();
    }

    /**
     * 日志webFilter
     *
     * @return
     */
    @Order(-2)
    @Bean
    public TraceWebFilter traceWebFilter() {
        return new TraceWebFilter();
    }

    /**
     * 拦截错误
     *
     * @param errorAttributes
     * @param serverCodecConfigurer
     * @param webProperties
     * @param applicationContext
     * @return
     */
    @Bean
    @Order(-2)
    public ErrorWebExceptionHandler errorExceptionHandler(ErrorAttributes errorAttributes,
        ServerCodecConfigurer serverCodecConfigurer, WebProperties webProperties,
        ApplicationContext applicationContext) {
        return new WebFluxErrorExceptionHandler(errorAttributes, webProperties.getResources(), serverCodecConfigurer,
            applicationContext);
    }

}
