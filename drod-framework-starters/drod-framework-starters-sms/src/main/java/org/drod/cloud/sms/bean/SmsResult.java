package org.drod.cloud.sms.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 返回对象
 *
 * @author: mist
 * @create: 2022-06-14 17:42
 **/

public class SmsResult {

    private static final  String STATUES_SUCCESS = "success";

    /**
     * 是否发送成功
     */
    @JsonIgnore
    private boolean sendSuccess;

    /**
     * 请求状态
     */
    private String status ;

    /**
     * 唯一发送 ID
     */
    @JsonProperty("send_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sendId;

    /**
     * 计费条数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int fee;

    /**
     * 失败码
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    /**
     * 失败信息描述
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    public SmsResult() {
    }

    public SmsResult(boolean sendSuccess, String msg) {
        this.sendSuccess = sendSuccess;
        this.msg = msg;
    }

    public static SmsResult getResult(String response){

        ObjectMapper objectMapper = new ObjectMapper();
        SmsResult smsResult = null;

        try {
            smsResult = objectMapper.readValue(response,SmsResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return SmsResult.error("解析对象报错");
        }

        if(STATUES_SUCCESS.equals(smsResult.getStatus())){
            return SmsResult.success("短信发送成功");
        }else {
            return SmsResult.error("短信发送失败，"+smsResult.getMsg());
        }
    }

    public static SmsResult success(String msg){
        return new SmsResult(true,msg);
    }

    public static SmsResult error(String msg){
        return new SmsResult(false,msg);
    }

    public boolean isSendSuccess() {
        return sendSuccess;
    }

    public void setSendSuccess(boolean sendSuccess) {
        this.sendSuccess = sendSuccess;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
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
