package org.drod.cloud.email.bean;

public class MailResult {
    /**
     * 是否发送成功
     */
    private String sendSuccess;

    /**
     * 请求状态
     */
    private String status ;

    /**
     * 失败码
     */
    private String code;

    /**
     * 失败信息描述
     */
    private String msg;


    public static MailResult getSuccess(String sendSuccess, String code, String status, String msg){
        MailResult result = new MailResult();
        result.setSendSuccess(sendSuccess);
        result.setCode(code);
        result.setStatus(status);
        result.setMsg(msg);
        return result;

    }

    public String isSendSuccess() {
        return sendSuccess;
    }

    public void setSendSuccess(String sendSuccess) {
        this.sendSuccess = sendSuccess;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
