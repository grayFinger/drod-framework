package org.drod.cloud.gateway.filter;

import org.drod.framework.util.TraceUtils;
import org.drod.framework.web.common.HttpRequestConstants;
import org.drod.framework.web.common.HttpResultConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 初始化上下文过滤器
 *
 * @author drod
 */
public class GatewayTraceIdRequestGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String traceId = exchange.getRequest().getHeaders().getFirst(HttpResultConstants.TRACE_ID);
        // 不存在则生成一份
        if (StringUtils.isBlank(traceId)) {
            traceId = TraceUtils.getTraceId();
        }
        exchange.getAttributes().put(HttpResultConstants.TRACE_ID, traceId);
        // 传值
        exchange.getRequest().mutate().header(HttpRequestConstants.TRACE_ID, traceId).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
