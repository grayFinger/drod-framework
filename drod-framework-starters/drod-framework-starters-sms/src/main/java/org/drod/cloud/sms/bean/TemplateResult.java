package org.drod.cloud.sms.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class TemplateResult {

    /**
     * 模板id
     */
    @JsonProperty("template_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String templateId;

    /**
     * 模板标题
     */
    @JsonProperty("sms_title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String smsTitle;

    /**
     *短信模板签名
     */
    @JsonProperty("sms_signature")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String smsSignature;

    /**
     * 短信模板正文
     */
    @JsonProperty("sms_content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String smsContent;

    /**
     * 新增时间
     */
    @JsonProperty("add_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp addDate;

    /**
     * 最后一次编辑时间
     */
    @JsonProperty("edit_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp editDate;

    /**
     * 模板状态
     */
    @JsonProperty("template_status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int templateStatus;

    /**
     * 模板状态描述
     */
    @JsonProperty("template_status_description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String templateStatusDescription;

    /**
     * 审核被拒绝原因
     */
    @JsonProperty("template_reject_reson")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String templateRejectReson;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getSmsTitle() {
        return smsTitle;
    }

    public void setSmsTitle(String smsTitle) {
        this.smsTitle = smsTitle;
    }

    public String getSmsSignature() {
        return smsSignature;
    }

    public void setSmsSignature(String smsSignature) {
        this.smsSignature = smsSignature;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Timestamp getAddDate() {
        return addDate;
    }

    public void setAddDate(Timestamp addDate) {
        this.addDate = addDate;
    }

    public Timestamp getEditDate() {
        return editDate;
    }

    public void setEditDate(Timestamp editDate) {
        this.editDate = editDate;
    }

    public int getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(int templateStatus) {
        this.templateStatus = templateStatus;
    }

    public String getTemplateStatusDescription() {
        return templateStatusDescription;
    }

    public void setTemplateStatusDescription(String templateStatusDescription) {
        this.templateStatusDescription = templateStatusDescription;
    }

    public String getTemplateRejectReson() {
        return templateRejectReson;
    }

    public void setTemplateRejectReson(String templateRejectReson) {
        this.templateRejectReson = templateRejectReson;
    }
}
