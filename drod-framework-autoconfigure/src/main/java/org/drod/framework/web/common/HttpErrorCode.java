package org.drod.framework.web.common;

public enum HttpErrorCode {

    DEFAULT_STATUS(417), INVALID_REQUEST_STATUS(400), NO_HANDLER_FOUND_STATUS(404), INNER_EXCEPTION_STATUS(500),
    METHOD_NOT_SUPPORT(415);

    private int value;

    HttpErrorCode(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
