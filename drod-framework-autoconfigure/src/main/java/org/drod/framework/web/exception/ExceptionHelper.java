package org.drod.framework.web.exception;

import org.drod.framework.web.common.HttpErrorCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常helper类
 *
 * @author e9
 */
public class ExceptionHelper {

    public static Error parseException(Throwable ex) {

        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return Error.builder().code(HttpErrorCode.METHOD_NOT_SUPPORT.value()).message("Request method not support")
                .build();
        }

        if (ex instanceof HttpMediaTypeNotSupportedException) {
            return Error.builder().code(HttpErrorCode.METHOD_NOT_SUPPORT.value())
                .message("Request media type not support").build();
        }

        if (ex instanceof MissingPathVariableException) {
            return Error.builder().code(HttpErrorCode.INNER_EXCEPTION_STATUS.value()).message("Miss path variable")
                .build();
        }

        if (ex instanceof MissingServletRequestParameterException || ex instanceof ServletRequestBindingException
            || ex instanceof MethodArgumentNotValidException) {
            return Error.builder().code(HttpErrorCode.INVALID_REQUEST_STATUS.value()).message("Parameters not valid")
                .build();
        }

        if (ex instanceof TypeMismatchException) {
            return Error.builder().code(HttpErrorCode.INVALID_REQUEST_STATUS.value())
                .message("Parameters type miss match").build();
        }

        if (ex instanceof MissingServletRequestPartException) {
            return Error.builder().code(HttpErrorCode.INVALID_REQUEST_STATUS.value()).message("Request part miss")
                .build();
        }

        if (ex instanceof BindException) {
            return Error.builder().code(HttpErrorCode.INVALID_REQUEST_STATUS.value()).message("Parameters bind error")
                .build();
        }

        if (ex instanceof HttpMediaTypeNotAcceptableException) {
            return Error.builder().code(HttpErrorCode.INVALID_REQUEST_STATUS.value()).message("Parameters format error")
                .build();
        }

        if (ex instanceof AsyncRequestTimeoutException) {
            return Error.builder().code(HttpErrorCode.INNER_EXCEPTION_STATUS.value()).message("Request timeout")
                .build();
        }

        if (ex instanceof ResponseStatusException) {
            return Error.builder().code(((ResponseStatusException)ex).getStatus().value()).message("Resource not found")
                .build();
        }

        if (ex instanceof NoHandlerFoundException) {
            return Error.builder().code(HttpErrorCode.NO_HANDLER_FOUND_STATUS.value()).message("Url not found").build();
        }

        return Error.builder().code(HttpErrorCode.INNER_EXCEPTION_STATUS.value()).message("Internal Server Error")
            .build();
    }

    @Builder
    @Data
    public static class Error {

        private int code;

        private String message;

    }
}
