package org.drod.framework.webflux.exception;

import com.alibaba.fastjson.JSONObject;
import org.drod.framework.feign.FeignContext;
import org.drod.framework.util.TraceUtils;
import org.drod.framework.web.common.HttpResultConstants;
import org.drod.framework.web.exception.BizException;
import org.drod.framework.web.exception.ExceptionHelper;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * webflux全局处理
 *
 * @author e9
 */
@Order(-2)
public class WebFluxErrorExceptionHandler extends AbstractErrorWebExceptionHandler {

    public WebFluxErrorExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
        ServerCodecConfigurer serverCodecConfigurer, ApplicationContext applicationContext) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        Map<String, Object> errorMap = new HashMap<>();
        // 获取到异常信息
        Throwable throwable = this.getError(request);
        HttpStatus httpStatus = null;
        if (throwable instanceof BizException) {
            // 此处异常一半为本jvm因业务场景抛出
            BizException bizException = (BizException)throwable;
            errorMap.putAll(bizException.toMap());
        } else {
            // 2种场景。1:feign调用抛出了Exception, 一种为本jvm捕捉到的异常，包含了feign以及内部的
            BizException bizException = FeignContext.getBizException();
            // 如果feign调用发生的则表示被调用的服务出现了异常
            if (Optional.ofNullable(bizException).isPresent()) {
                // 返回业务异常
                errorMap.putAll(bizException.toMap());
                // 清除异常
                FeignContext.clearException();
            } else {
                ExceptionHelper.Error error = ExceptionHelper.parseException(throwable);
                errorMap.put(HttpResultConstants.MSG, error.getMessage());
                errorMap.put(HttpResultConstants.CODE, error.getCode());
            }
        }
        errorMap.put(HttpResultConstants.TRACE_ID, TraceUtils.getTraceId());
        // 构建返回值
        return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(JSONObject.toJSONString(errorMap)));
    }

}
