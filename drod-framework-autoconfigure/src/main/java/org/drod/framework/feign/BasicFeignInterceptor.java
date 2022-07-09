package org.drod.framework.feign;

import org.drod.framework.web.HttpRequestContext;
import org.drod.framework.web.RequestContextHolder;
import org.drod.framework.web.common.HttpRequestConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Optional;

/**
 * 基本拦截器
 *
 * @author e9
 */
public class BasicFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpRequestContext context = RequestContextHolder.get();

        requestTemplate.header(HttpRequestConstants.USER_ID, context.getUserId());
        requestTemplate.header(HttpRequestConstants.ENTERPRISE_ID, context.getEnterpriseId());
        requestTemplate.header(HttpRequestConstants.IP, context.getUserIp());
        requestTemplate.header(HttpRequestConstants.ENTERPRISE_NAME, context.getEnterpriseName());
        requestTemplate.header(HttpRequestConstants.IS_LESSOR, context.getIsLessor());
        requestTemplate.header(HttpRequestConstants.USER_NAME, context.getUserName());
        requestTemplate.header(HttpRequestConstants.USER_TYPE, context.getUserType());
        requestTemplate.header(HttpRequestConstants.SOURCE_NAME, context.getSourceName());
        requestTemplate.header(HttpRequestConstants.USER_KEY, context.getUserKey());
        requestTemplate.header(HttpRequestConstants.LOGIN_USER, context.getLoginUser());
        requestTemplate.header(HttpRequestConstants.ACCESS_TOKEN, context.getAccessToken());
        requestTemplate.header(HttpRequestConstants.API_VER, context.getApiVer());
        requestTemplate.header(HttpRequestConstants.REMOTE_IP, context.getRemoteIp());
        //跟踪id
        requestTemplate.header(HttpRequestConstants.TRACE_ID, Optional.ofNullable(context).map(HttpRequestContext::getTraceId).orElse(null));
    }

}
