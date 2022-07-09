package org.drod.framework.web.exception;

public class ErrorParam {

    private final String name;
    private final String value;

    public ErrorParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
