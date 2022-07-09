package org.drod.framework.web.exception;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;

public abstract class GlobalExceptionResolver implements HandlerExceptionResolver, Ordered {
}
