package org.drod.cloud.webflux;

import org.drod.framework.webflux.config.WebFilterAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({WebFilterAutoConfiguration.class})
public @interface EnableWebFlux {}
