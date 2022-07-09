package org.drod.cloud.gateway.config;

import org.drod.cloud.gateway.exception.GatewayErrorExceptionHandler;
import org.drod.cloud.gateway.filter.GatewayResponseGlobalFilter;
import org.drod.cloud.gateway.filter.GatewayTraceIdRequestGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 网关
 *
 * @author drod
 */
@Configuration
public class GatewayFilterAutoConfiguration {

    /**
     * 初始化请求上下文web过滤器
     *
     * @return
     */
    @Bean
    public GatewayTraceIdRequestGlobalFilter initRequestContextWebFilter() {
        return new GatewayTraceIdRequestGlobalFilter();
    }

    /**
     * 全局响应包装
     *
     * @return
     */
    @Bean
    public GatewayResponseGlobalFilter responseGlobalFilter() {
        return new GatewayResponseGlobalFilter();
    }

    /**
     * 拦截错误
     *
     * @return
     */
    @Bean
    @Order(-2)
    public GatewayErrorExceptionHandler errorExceptionHandler() {
        return new GatewayErrorExceptionHandler();
    }

}
