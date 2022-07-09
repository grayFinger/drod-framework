package org.drod.framework.feign;

public interface CanaryConstants {

    // 灰度版本号，在HTTP Header中传递
    String HEADER_CANARY_VERSION = "canary-version";

}
