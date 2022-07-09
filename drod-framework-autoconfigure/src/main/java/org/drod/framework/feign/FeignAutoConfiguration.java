package org.drod.framework.feign;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign自动配置
 *
 * @author e9
 */
@Configuration
@ConditionalOnClass( value = Feign.class)
public class FeignAutoConfiguration {
    /**
     * feign请求拦截器。
     * @return
     */
    @Bean
    public FeignClientRequestInterceptor feignInterceptor(){
        return new FeignClientRequestInterceptor();
    }

    @Bean
    public BasicFeignInterceptor basicFeignInterceptor(){
        return new BasicFeignInterceptor();
    }

}
