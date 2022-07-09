package org.drod.framework.feign;

import java.util.Map;

public class BackgroundContext {

    private boolean inBackground;

    private String canaryVersion;

    private Map<String, String> forwardedContextMap;

    public boolean isInBackground() {
        return inBackground;
    }

    public void setInBackground(boolean inBackground) {
        this.inBackground = inBackground;
    }

    public String getCanaryVersion() {
        return canaryVersion;
    }

    public void setCanaryVersion(String canaryVersion) {
        this.canaryVersion = canaryVersion;
    }

    public Map<String, String> getForwardedContextMap() {
        return forwardedContextMap;
    }

    public void setForwardedContextMap(Map<String, String> forwardedContextMap) {
        this.forwardedContextMap = forwardedContextMap;
    }

    public void clear() {
        this.inBackground = false;
        this.canaryVersion = null;
        this.forwardedContextMap = null;
    }

    private static final ThreadLocal<BackgroundContext> context = new ThreadLocal<BackgroundContext>() {
        @Override
        protected BackgroundContext initialValue() {
            return new BackgroundContext();
        }
    };

    public static BackgroundContext get() {
        return context.get();
    }

}
