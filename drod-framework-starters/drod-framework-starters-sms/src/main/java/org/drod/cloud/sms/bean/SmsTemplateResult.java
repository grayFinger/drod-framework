package org.drod.cloud.sms.bean;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Deng
 * 2022-06-28
 */
public class SmsTemplateResult {

    private String status;

    private String code;

    private String msg;

    @JsonProperty("start_row")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int startRow;

    @JsonProperty("end_row")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int endRow;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TemplateResult> templates;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public List<TemplateResult> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateResult> templates) {
        this.templates = templates;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

