package org.drod.cloud.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 发送对象
 *
 * @author Mist
 * @create: 2022-06-10 13:50
 **/
@ConfigurationProperties(prefix = "drod.submail.sms")
public class SmsProperties {

    /**
     * 短信应用ID
     */
    private String appId;

    /**
     * app_key 短信签名 = signature
     */
    private String appKey;

    /**
     * 短信模板签名
     */
    private String templateSignature;


    public SmsProperties() {
    }

    public SmsProperties(String appId, String appKey) {
        this.appId = appId;
        this.appKey = appKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTemplateSignature() {
        return templateSignature;
    }

    public void setTemplateSignature(String templateSignature) {
        this.templateSignature = templateSignature;
    }
}
