package org.drod.framework.web.exception;

import com.alibaba.fastjson.JSONObject;
import org.drod.framework.feign.FeignContext;
import org.drod.framework.util.TraceUtils;
import org.drod.framework.web.common.HttpResultConstants;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.encoding.HttpEncoding;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 异常处理，拦截mvc异常时返回结果
 *
 * @author e9
 */
public class BizExceptionResolver extends GlobalExceptionResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizExceptionResolver.class);

    @Value("#{'${drod.exception.remove.headers:access_token,cookie,authorization,accesstoken}'.split(',')}")
    private List<String> removeHeaders = new ArrayList<>();

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        // 构建异常返回对象
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put(HttpResultConstants.TRACE_ID, TraceUtils.getTraceId());
        // 处理异常
        if (ex instanceof BizException) {
            LOGGER.error("An BizException has occurred , url : {} , exception : {} ",
                request.getRequestURL().toString(), ex.getMessage());
            // 此处异常一半为本jvm因业务场景抛出
            BizException bizException = (BizException)ex;
            errorMap.putAll(bizException.toMap());
        } else {
            // 2种场景：feign调用抛出了熔断异常、本jvm捕捉到的异常，包含了feign以及内部的
            BizException bizException = FeignContext.getBizException();
            // 如果feign调用发生的则表示被调用的服务出现了异常
            int status;
            if (Optional.ofNullable(bizException).isPresent()) {
                LOGGER.error("An BizException has occurred ,url : {}  : error : {}", request.getRequestURL().toString(),
                    ex.getMessage());
                // 返回业务异常
                errorMap.putAll(bizException.toMap());
                // 清除异常
                FeignContext.clearException();
            } else {
                LOGGER.error("An Exception has occurred ,url : {} ,  parameters: {}, headers : {}, contentType: {} ",
                    request.getRequestURL().toString(), getRequestParameters(request), getRequestHeaders(request),
                    request.getContentType(), ex);
                // 获取异常信息
                ExceptionHelper.Error error = ExceptionHelper.parseException(ex);
                errorMap.put(HttpResultConstants.MSG, error.getMessage());
                errorMap.put(HttpResultConstants.CODE, error.getCode());
            }
        }


        // 构建返回对象
        response.setHeader(HttpEncoding.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        write(JSONObject.toJSONString(errorMap), response);
        return new ModelAndView();
    }

    /**
     * 写返回流数据
     *
     * @param json
     * @param response
     */
    private void write(String json, HttpServletResponse response) {
        try (OutputStream output = response.getOutputStream()) {
            IOUtils.write(json, output, StandardCharsets.UTF_8);
        } catch (IOException ioex) {
            LOGGER.error("write response error,", ioex);
        }
    }

    /**
     * 获取request参数信息
     *
     * @param request
     * @return
     */
    private String getRequestParameters(HttpServletRequest request) {
        return JSONObject
            .toJSONString(Optional.ofNullable(request.getParameterMap()).orElseGet(() -> new HashMap<>(1, 1)));
    }

    /**
     * 获取header信息
     *
     * @param request
     * @return
     */
    private String getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>(32, 1);
        Enumeration<?> headerNames = request.getHeaderNames();
        if (null != headerNames) {
            while (headerNames.hasMoreElements()) {
                String name = (String)headerNames.nextElement();
                if (!removeHeaders.contains(name)) {
                    String value = request.getHeader(name);
                    headerMap.put(name, value);
                }
            }
        }
        return JSONObject.toJSONString(headerMap);
    }

}
