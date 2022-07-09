package org.drod.framework.feign;

public class RequireHeaderProvider {

    private static final String[] HEADERS = new String[]{"canary-version"};

    public RequireHeaderProvider() {
    }

    public static String[] getForwardedHeaders() {
        return HEADERS;
    }
}
