package org.drod.framework.web.interceptor;

import cn.hutool.core.util.StrUtil;
import org.drod.framework.util.TraceUtils;
import org.drod.framework.web.HttpRequestContext;
import org.drod.framework.web.RequestContextHolder;
import org.drod.framework.web.common.HttpRequestConstants;
import com.google.common.base.Charsets;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * web拦截器
 *
 * @author e9
 */
public class WebContextInterceptor implements HandlerInterceptor {

    /**
     * 应用名
     */
    private String app;

    public WebContextInterceptor(String app) {
        this.app = app;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        // 使用UtilTrace.getTraceId
        String traceId = TraceUtils.getTraceId(request);

        String ip = request.getRemoteAddr();
        String userId = getHeader(request, HttpRequestConstants.USER_ID);
        String enterpriseId = getHeader(request, HttpRequestConstants.ENTERPRISE_ID);

        // 给上下文赋值
        HttpRequestContext context = RequestContextHolder.get();
        context.setApp(app);
        context.setUserIp(ip);
        context.setTraceId(traceId);
        context.setUserId(userId);
        context.setEnterpriseId(enterpriseId);
        context.setEnterpriseName(getHeader(request, HttpRequestConstants.ENTERPRISE_NAME));
        context.setIsLessor(getHeader(request, HttpRequestConstants.IS_LESSOR));
        context.setUserName(getHeader(request, HttpRequestConstants.USER_NAME));
        context.setUserType(getHeader(request, HttpRequestConstants.USER_TYPE));
        context.setSourceName(getHeader(request, HttpRequestConstants.SOURCE_NAME));
        context.setUserKey(getHeader(request, HttpRequestConstants.USER_KEY));
        context.setLoginUser(getHeader(request, HttpRequestConstants.LOGIN_USER));
        context.setAccessToken(getHeader(request, HttpRequestConstants.ACCESS_TOKEN));
        context.setApiVer(getHeader(request, HttpRequestConstants.API_VER));
        context.setRemoteIp(getHeader(request, HttpRequestConstants.REMOTE_IP));

        // 线程上下文初始化
        RequestContextHolder.init(context);
        MDC.put(HttpRequestConstants.APP, app);
        MDC.put(HttpRequestConstants.TRACE_ID, traceId);
        MDC.put(HttpRequestConstants.USER_ID, userId);
        MDC.put(HttpRequestConstants.ENTERPRISE_ID, enterpriseId);
        MDC.put(HttpRequestConstants.IP, ip);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        response.setHeader(HttpRequestConstants.TRACE_ID, TraceUtils.getTraceId());
        // 清理资源
        RequestContextHolder.remove();
        MDC.remove(HttpRequestConstants.APP);
        MDC.remove(HttpRequestConstants.IP);
        MDC.remove(HttpRequestConstants.TRACE_ID);
        MDC.remove(HttpRequestConstants.USER_ID);
        MDC.remove(HttpRequestConstants.ENTERPRISE_ID);
    }

    /**
     * 获取头部参数值
     *
     * @param request
     * @param name
     * @return
     */
    private static String getHeader(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (StrUtil.isEmpty(value)) {
            return StrUtil.EMPTY;
        }
        return urlDecode(value);
    }

    /**
     * 内容解码
     *
     * @param str
     *            内容
     * @return 解码后的内容
     */
    private static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, Charsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return StrUtil.EMPTY;
        }
    }

}
