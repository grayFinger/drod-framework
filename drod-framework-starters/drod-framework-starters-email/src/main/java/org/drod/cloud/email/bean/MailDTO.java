package org.drod.cloud.email.bean;

import java.util.Date;

public class MailDTO {

    /**
     * 邮件收件人
     */
    private String[] to;
    /**
     * 邮件抄送人
     */
    private String[] cc;
    /**
     * 邮件密送人
     */
    private String[] bcc;
    /**
     *回复
     */
    private String replyTo;
    /**
     * 发送时间 可选，默认获取当前系统时间作为发送时间
     */
    private Date sentDate;
    /**
     * 邮件的主题
     */
    private String subject;
    /**
     * 如果邮件内容是纯文本
     */
    private String content;

    private String[] filenames;


    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getFilenames() {
        return filenames;
    }

    public void setFilenames(String[] filenames) {
        this.filenames = filenames;
    }
}
