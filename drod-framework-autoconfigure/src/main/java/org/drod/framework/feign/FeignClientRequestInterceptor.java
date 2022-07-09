package org.drod.framework.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 灰度拦截器
 *
 * @author e9
 */
public class FeignClientRequestInterceptor implements RequestInterceptor {

    @Value("${canary.enabled:false}")
    private boolean isCanaryService;

    @Value("${canary.version:}")
    private String canaryVersion;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String[] headers = new String[]{};
        // 是一个后台服务
        if (attrs == null) {
            BackgroundContext ctx = BackgroundContext.get();
            if (ctx.isInBackground()) {
                String cv = ctx.getCanaryVersion();
                if (StringUtils.isNotBlank(cv)) {
                    requestTemplate.header(CanaryConstants.HEADER_CANARY_VERSION, cv);
                }

                Map<String, String> contextMap = ctx.getForwardedContextMap();
                if (contextMap != null) {
                    if (headers != null) {
                        for (String name : headers) {
                            String value = contextMap.get(name);
                            if (value != null) {
                                requestTemplate.header(name, value);
                            }
                        }
                    } else {
                        for (Map.Entry<String, String> e : contextMap.entrySet()) {
                            requestTemplate.header(e.getKey(), e.getValue());
                        }
                    }
                }
            } else if (isCanaryService && StringUtils.isNotBlank(canaryVersion)) {
                requestTemplate.header(CanaryConstants.HEADER_CANARY_VERSION, canaryVersion);
            }

            return;
        }

        if (headers != null) {
            HttpServletRequest leadRequest = attrs.getRequest();
            for (String name : headers) {
                String value = leadRequest.getHeader(name);
                if (value != null) {
                    requestTemplate.header(name, value);
                }
            }
        }
    }
}
