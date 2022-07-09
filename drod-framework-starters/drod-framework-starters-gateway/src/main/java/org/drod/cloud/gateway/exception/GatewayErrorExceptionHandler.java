package org.drod.cloud.gateway.exception;

import com.alibaba.fastjson.JSONObject;
import org.drod.framework.util.TraceUtils;
import org.drod.framework.web.common.HttpResultConstants;
import org.drod.framework.web.exception.BizException;
import org.drod.framework.web.exception.ExceptionHelper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class GatewayErrorExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        // 获取网关下层传参
        String traceId = Optional.ofNullable(exchange.getAttributes().get(HttpResultConstants.TRACE_ID))
            .map(String::valueOf).orElse(TraceUtils.getTraceId());

        // 构建返回参数
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put(HttpResultConstants.TRACE_ID, traceId);

        HttpStatus httpStatus;
        if (ex instanceof BizException) {
            // 获取异常信息
            BizException bizException = (BizException)ex;
            httpStatus = HttpStatus.valueOf(bizException.getCode());
            errorMap.putAll(bizException.toMap());
        } else {
            // 获取异常信息
            ExceptionHelper.Error error = ExceptionHelper.parseException(ex);
            errorMap.put(HttpResultConstants.MSG, error.getMessage());
            errorMap.put(HttpResultConstants.CODE, error.getCode());
            httpStatus = HttpStatus.valueOf(error.getCode());
        }

        // 组装返回值
        response.setStatusCode(httpStatus);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(errorMap).getBytes());

        return response.writeWith(Mono.just(dataBuffer));
    }
}
