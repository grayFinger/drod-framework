package org.drod.framework.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Configuration
public class CorsFilterConfiguration {

    @Bean
    @ConditionalOnProperty(value = "web.cors.enable", havingValue = "true")
    public FilterRegistrationBean<org.springframework.web.filter.CorsFilter> regCorsFilter(
            @Value("${drod.web.cors.url-patterns:/*}") String urlPatterns,
            @Value("${drod.web.cors.register-path:/**}") String regPath,
            @Value("#{'${drod.web.cors.allowed-origins:}'.split(',')}") List<String> allowedOrigins,
            @Value("#{'${drod.web.cors.allowed-headers:*}'.split(',')}") List<String> allowedHeaders,
            @Value("#{'${drod.web.cors.allowed-methods:GET,HEAD,POST}'.split(',')}") List<String> allowedMethods,
            @Value("#{'${drod.web.cors.exposed-headers:}'.split(',')}") List<String> exposedHeaders,
            @Value("${drod.web.cors.max-age:1800}") Long maxAge) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(trimEmpty(allowedOrigins));
        config.setAllowedHeaders(trimEmpty(allowedHeaders));
        config.setAllowedMethods(trimEmpty(allowedMethods));
        config.setExposedHeaders(trimEmpty(exposedHeaders));
        config.setMaxAge(maxAge);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(regPath, config);

        FilterRegistrationBean<org.springframework.web.filter.CorsFilter> result = new FilterRegistrationBean<>(new org.springframework.web.filter.CorsFilter(source));
        result.setName("cors");
        result.addUrlPatterns(urlPatterns);
        result.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        return result;
    }


    /** trim后，移除""和null对象 */
    public static List<String> trimEmpty(Collection<String> list) {
        List<String> result = new ArrayList<>(list.size());
        for (String str : list) {
            if (str != null) {
                str = str.trim();
                if (!str.isEmpty()) result.add(str);
            }
        }
        return result;
    }

}
