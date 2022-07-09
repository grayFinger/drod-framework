package org.drod.framework.web;

/**
 * APP请求参数上下文
 *
 * @author e9
 */
public class HttpRequestContext {

    /**
     * 企业id
     */
    private String enterpriseId;

    /**
     * 企业账号
     */
    private String enterpriseName;

    /**
     * 企业类型
     */
    private String isLessor;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 租户策略源
     */
    private String sourceName;

    /**
     * 用户标识
     */
    private String userKey;

    /**
     * 登录用户
     */
    private String loginUser;

    /**
     * 令牌
     */
    private String accessToken;

    /**
     * api版本
     */
    private String apiVer;

    /**
     * 当前应用名
     */
    private String app;

    /**
     * 跟踪id
     */
    private String traceId;

    /**
     * 远程调试ip
     */
    private String remoteIp;

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getIsLessor() {
        return isLessor;
    }

    public void setIsLessor(String isLessor) {
        this.isLessor = isLessor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getApiVer() {
        return apiVer;
    }

    public void setApiVer(String apiVer) {
        this.apiVer = apiVer;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }
}
