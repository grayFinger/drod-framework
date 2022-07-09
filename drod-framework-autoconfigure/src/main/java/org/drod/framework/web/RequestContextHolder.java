package org.drod.framework.web;

import java.util.Objects;

/**
 * 请求变量holder
 *
 * @author e9
 */
public class RequestContextHolder {

    /**
     * 线程变量
     */
    private static final ThreadLocal<HttpRequestContext> contextLocal = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new HttpRequestContext();
        }
    };

    /**
     * 初始化context
     *
     * @param httpRequestContext
     */
    public static void init(HttpRequestContext httpRequestContext) {
        contextLocal.remove();
        if (!Objects.isNull(httpRequestContext)) {
            contextLocal.set(httpRequestContext);
        }
    }

    public static HttpRequestContext get() {
        return contextLocal.get();
    }

    public static void remove() {
        contextLocal.remove();
    }


}
