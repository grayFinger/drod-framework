package org.drod.framework.web.exception;

import com.alibaba.fastjson.JSONObject;
import org.drod.framework.web.common.HttpErrorCode;
import org.drod.framework.web.common.HttpResultConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 定义顶层业务异常类,所有异常类需要继承此类
 *
 * @author e9
 */
public final class BizException extends RuntimeException {

    private int code;
    private String msg;
    private String lazyJson;
    private ErrorParam[] parameters;
    private Enum<? extends HttpErrorCode> errorCode;

    public BizException(int code, String message) {
        this.msg = message;
        this.code = code;
    }

    public BizException(String message) {
        this.msg = message;
        this.code = HttpErrorCode.DEFAULT_STATUS.value();
    }

    public BizException(String message, ErrorParam... parameters) {
        this.msg = message;
        this.parameters = parameters;
    }

    public BizException(int code, String message, ErrorParam... parameters) {
        this.msg = message;
        this.code = code;
        this.parameters = parameters;
    }

    public BizException(int code, Enum<? extends HttpErrorCode> errorCode, ErrorParam... parameters) {
        this.code = code;
        this.msg = errorCode.name();
        this.errorCode = errorCode;
        this.parameters = parameters;
    }

    public BizException(Enum<? extends HttpErrorCode> errorCode) {
        this(HttpErrorCode.DEFAULT_STATUS.value(), errorCode);
    }

    public BizException(Enum<? extends HttpErrorCode> errorCode, ErrorParam... parameters) {
        this(HttpErrorCode.DEFAULT_STATUS.value(), errorCode, parameters);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> buildMap = buildMap(code, msg, parameters);
        return buildMap;
    }

    private static Map<String, Object> buildMap(int code, String msg, ErrorParam[] parameters) {
        Map<String, Object> map = new HashMap<>();
        map.put(HttpResultConstants.CODE, code);
        map.put(HttpResultConstants.MSG, msg);
        map.put(HttpResultConstants.PARAMETERS, parameters);
        return map;
    }

    public static BizException fromJson(String json) {
        if (Objects.isNull(json)) {
            return null;
        }
        Map<String, Object> parseObject = JSONObject.parseObject(json, HashMap.class);
        int code = (int)parseObject.get(HttpResultConstants.CODE);
        String msg = (String)parseObject.get(HttpResultConstants.MSG);
        String namespace = (String)parseObject.get(HttpResultConstants.NAMESPACE);
        if (Objects.nonNull(namespace)) {
            try {
                Class ecClazz = Class.forName(namespace);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        BizException bizException = new BizException(code, msg);
        List<Map<String, Object>> params = (List<Map<String, Object>>)parseObject.get(HttpResultConstants.PARAMETERS);
        if (Objects.nonNull(params)) {
            ErrorParam[] parameters = new ErrorParam[params.size()];
            for (int i = 0; i < params.size(); i++) {
                String name = (String)params.get(i).get(HttpResultConstants.PARAM_NAME);
                String value = (String)params.get(i).get(HttpResultConstants.PARAM_VALUE);
                parameters[i] = new ErrorParam(name, value);
            }
            bizException.setParameters(parameters);
        }
        return bizException;
    }

    @Override
    public String getMessage() {
        if (!Objects.isNull(lazyJson)) {
            return lazyJson;
        }
        Map<String, Object> buildMap = buildMap(code, msg, parameters);

        if (Objects.nonNull(errorCode)) {
            buildMap.put(HttpResultConstants.NAMESPACE, errorCode.getClass().getName());
        }
        try {
            lazyJson = new ObjectMapper().writeValueAsString(buildMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return lazyJson;
    }

    /**
     * 避免构建异常堆栈付出的开销
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLazyJson() {
        return lazyJson;
    }

    public void setLazyJson(String lazyJson) {
        this.lazyJson = lazyJson;
    }

    public ErrorParam[] getParameters() {
        return parameters;
    }

    public void setParameters(ErrorParam[] parameters) {
        this.parameters = parameters;
    }

    public Enum<? extends HttpErrorCode> getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Enum<? extends HttpErrorCode> errorCode) {
        this.errorCode = errorCode;
    }
}
