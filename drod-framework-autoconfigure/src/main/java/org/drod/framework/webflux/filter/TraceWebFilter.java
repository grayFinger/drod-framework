package org.drod.framework.webflux.filter;

import org.drod.framework.util.TraceUtils;
import org.drod.framework.web.RequestContextHolder;
import org.drod.framework.web.common.HttpResultConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 链路过滤器
 *
 * @author e9
 */
public class TraceWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {

        String traceId = serverWebExchange.getRequest().getHeaders().getFirst(HttpResultConstants.TRACE_ID);
        // 不存在则生成一份
        if (StringUtils.isBlank(traceId)) {
            traceId = TraceUtils.getTraceId();
        }

        RequestContextHolder.get().setTraceId(traceId);
        return webFilterChain.filter(serverWebExchange);
    }

}
