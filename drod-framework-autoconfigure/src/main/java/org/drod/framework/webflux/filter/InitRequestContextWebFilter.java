package org.drod.framework.webflux.filter;

import org.drod.framework.util.NetUtils;
import org.drod.framework.web.HttpRequestContext;
import org.drod.framework.web.RequestContextHolder;
import org.drod.framework.web.common.HttpRequestConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * 初始化上下文过滤器
 *
 * @author e9
 */
public class InitRequestContextWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        // 获取ip
        InetSocketAddress inetSocketAddress = serverWebExchange.getRequest().getRemoteAddress();
        String ip = Optional.ofNullable(inetSocketAddress).map(address -> address.getAddress().getHostAddress())
            .orElseGet(() -> NetUtils.getIpAddress(serverWebExchange.getRequest()));

        HttpHeaders httpHeaders = serverWebExchange.getRequest().getHeaders();
        // 组装上下文
        HttpRequestContext context = RequestContextHolder.get();
        context.setUserIp(ip);
        context.setTraceId(httpHeaders.getFirst(HttpRequestConstants.TRACE_ID));
        context.setUserId(httpHeaders.getFirst(HttpRequestConstants.USER_ID));
        context.setEnterpriseId(httpHeaders.getFirst(HttpRequestConstants.ENTERPRISE_NAME));
        context.setEnterpriseName(httpHeaders.getFirst(HttpRequestConstants.ENTERPRISE_NAME));
        context.setIsLessor(httpHeaders.getFirst(HttpRequestConstants.IS_LESSOR));
        context.setUserName(httpHeaders.getFirst(HttpRequestConstants.USER_NAME));
        context.setUserType(httpHeaders.getFirst(HttpRequestConstants.USER_TYPE));
        context.setSourceName(httpHeaders.getFirst(HttpRequestConstants.SOURCE_NAME));
        context.setUserKey(httpHeaders.getFirst(HttpRequestConstants.USER_KEY));
        context.setLoginUser(httpHeaders.getFirst(HttpRequestConstants.LOGIN_USER));
        context.setAccessToken(httpHeaders.getFirst(HttpRequestConstants.ACCESS_TOKEN));
        context.setApiVer(httpHeaders.getFirst(HttpRequestConstants.API_VER));
        context.setRemoteIp(httpHeaders.getFirst(HttpRequestConstants.REMOTE_IP));

        // 线程上下文初始化
        RequestContextHolder.init(context);
        return webFilterChain.filter(serverWebExchange);
    }

}
