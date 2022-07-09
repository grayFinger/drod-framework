package org.drod.cloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import org.drod.framework.web.RequestContextHolder;
import org.drod.framework.web.common.HttpRequestConstants;
import org.drod.framework.web.common.HttpResultConstants;
import com.google.common.base.Charsets;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 全局处理返回值
 *
 * @author drod
 */
@Configuration
public class GatewayResponseGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取response
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        // 处理响应过滤
        ServerHttpResponseDecorator response = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffers);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        DataBufferUtils.release(join);
                        // 获取网关下层传参
                        String traceId =
                            Optional.ofNullable(exchange.getAttributes().get(HttpRequestConstants.TRACE_ID))
                                .map(String::valueOf).orElse(null);
                        // 组装返回值
                        Map<String, Object> resultMap = new HashMap<>();
                        resultMap.put(HttpResultConstants.CODE, HttpStatus.OK.value());
                        resultMap.put(HttpResultConstants.TRACE_ID, traceId);
                        resultMap.put(HttpResultConstants.DATA, new String(content, Charsets.UTF_8));
                        // 序列化
                        byte[] uppedContent = JSONObject.toJSONString(resultMap).getBytes(Charsets.UTF_8);
                        originalResponse.getHeaders().setContentLength(uppedContent.length);
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };
        RequestContextHolder.remove();
        return chain.filter(exchange.mutate().response(response).build());
    }

    @Override
    public int getOrder() {
        // WRITE_RESPONSE_FILTER 之前执行
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

}
