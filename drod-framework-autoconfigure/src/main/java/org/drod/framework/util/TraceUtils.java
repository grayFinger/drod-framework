package org.drod.framework.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.drod.framework.web.RequestContextHolder;
import org.drod.framework.web.common.HttpRequestConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * trace工具方法
 *
 * @author e9
 */
public class TraceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceUtils.class);

    public static void setTrace(HttpServletRequest request) {
        init(request);
    }

    private static void init(HttpServletRequest request) {
        getTraceId(request);
    }

    public static String getTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(HttpRequestConstants.TRACE_ID);
        if (!StringUtils.isBlank(traceId)) {
            return traceId;
        }
        traceId = getTraceId();
        LOGGER.debug("traceId={}", traceId);
        return traceId;
    }

    public static String getTraceId() {
        return getTraceId(true);
    }

    private static String getTraceId(boolean autoCreate) {
        String traceId = RequestContextHolder.get().getTraceId();
        return StringUtils.isEmpty(traceId) ? (autoCreate ? nextIdStr() : null) : traceId;
    }

    private static String nextIdStr() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return snowflake.nextIdStr();
    }

}
