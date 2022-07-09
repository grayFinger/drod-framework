package org.drod.cloud.sms.api;


import org.apache.commons.lang3.StringUtils;
import org.drod.cloud.sms.bean.SmsTemplateResult;
import org.drod.cloud.sms.config.SmsProperties;
import org.drod.cloud.sms.http.SmsTemplateHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Deng
 * 2022-06-28
 */
public class TemplateApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateApi.class);

    @Autowired
    private SmsTemplateHttp smsHttp;

    private SmsProperties smsProperties;

    public TemplateApi(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    /**
     * 查询短信模板信息
     * @param templateId 模板id 可为空
     * @param offset   可选    默认0
     *                 （默认值为0，默认返回最近50个模板信息。offset=1时，查询第51-100个模板，offset=2时，查询第101-150个模板，以此类推。）
     * @return  SmsTemplateResult
     */
    public SmsTemplateResult getTemplates(String templateId, int offset){
        LOGGER.debug(">>>>>>>>>>> TemplateApi.getTemplates, params: templateId: {}, offset:{}", templateId, offset);
        return smsHttp.getTemplate(this.smsProperties.getAppId(),templateId,offset,this.smsProperties.getAppKey());
    }

    /**
     * 新增短信模板
     * @param smsTitle 短信模板标题
     * @param smsContent 短信模板内容
     * @return SmsTemplateResult
     */
    public SmsTemplateResult saveTemplates(String smsTitle,String smsContent){
        if (StringUtils.isBlank(smsTitle)){
            throw new NullPointerException("smsTitle  is null");
        }else if (StringUtils.isBlank(this.smsProperties.getTemplateSignature())){
            throw new NullPointerException("templateSignature is null 请检查配置文件e9.submail.sms.templateSignature");
        }else if (StringUtils.isBlank(smsContent)){
            throw new NullPointerException("smsContent is null");
        }
        return smsHttp.postTemplate(this.smsProperties.getAppId(),smsTitle,
                this.smsProperties.getTemplateSignature(),smsContent,
                this.smsProperties.getAppKey());
    }

    /**
     * 更新短信模板
     * @param templateId 模板id
     * @param smsTitle  模板标题
     * @param smsContent  模板内容
     * @return SmsTemplateResult
     */
    public SmsTemplateResult updateTemplates(String templateId,String smsTitle,String smsContent){
        if (StringUtils.isBlank(templateId)) {
            throw new NullPointerException("templateId  is null");
        } else if(StringUtils.isBlank(smsTitle)){
            throw new NullPointerException("smsTitle  is null");
        }else if(StringUtils.isBlank(this.smsProperties.getTemplateSignature())){
            throw new NullPointerException("templateSignature is null");
        }else if (StringUtils.isBlank(smsContent)){
            throw new NullPointerException("smsContent is null");
        }
        return smsHttp.putTemplate(this.smsProperties.getAppId(),templateId,smsTitle,
                this.smsProperties.getTemplateSignature(),smsContent,
                this.smsProperties.getAppKey());
    }

    /**
     * 删除短信模板
     * @param templateId  模板id
     * @return
     */
    public SmsTemplateResult deleteTemplate(String templateId){
        if (StringUtils.isBlank(templateId)) {
            throw new NullPointerException("templateId is null");
        }
        return smsHttp.deleteTemplate(this.smsProperties.getAppId(),templateId,
                this.smsProperties.getAppKey());
    }


}
