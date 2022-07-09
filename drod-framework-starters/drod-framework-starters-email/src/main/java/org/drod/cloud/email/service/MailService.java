package org.drod.cloud.email.service;


import org.apache.commons.lang3.ObjectUtils;
import org.drod.cloud.email.bean.MailDTO;
import org.drod.cloud.email.config.DrodMailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


public class MailService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    private DrodMailProperties drodMailProperties;

    public MailService(DrodMailProperties drodMailProperties){
        this.drodMailProperties = drodMailProperties;
    }

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * 发送多个附件邮件
     * to：发送给谁
     * subject：发送的主题（邮件主题）
     * content：发送的内容
     * filePath：附件路径
     * */
    public void sendAttachmentsMails(MailDTO mailDTO) throws MessagingException {
        LOGGER.debug(">>>>>>>>>>>  MailService.sendAttachmentsMails, params: mailDTO: {}", mailDTO);
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(mailDTO.getTo());
        if (ObjectUtils.isNotEmpty(mailDTO.getBcc())){
            mimeMessageHelper.setBcc(mailDTO.getBcc());
        }
        if (ObjectUtils.isNotEmpty(mailDTO.getCc())){
            mimeMessageHelper.setCc(mailDTO.getCc());
        }
        if (ObjectUtils.isNotEmpty(mailDTO.getSubject())){
            mimeMessageHelper.setSubject(mailDTO.getSubject());
        }
        mimeMessageHelper.setText(mailDTO.getContent(),true);

        //如果有文件 获取文件路径以及文件名称
        if (ObjectUtils.isNotEmpty(mailDTO.getFilenames())){
            for (String filepath:mailDTO.getFilenames()) {
                FileSystemResource fileSystemResource = new FileSystemResource(new File(filepath));
                String fileName = fileSystemResource.getFilename();
                assert fileName != null;
                mimeMessageHelper.addAttachment(fileName,fileSystemResource);
            }
        }

        mimeMessageHelper.setFrom(this.drodMailProperties.getFrom());

        try {
            javaMailSender.send(mimeMessage);
            LOGGER.info("----------email send success");
        } catch (MailException e) {
            LOGGER.error("-----------email send error",e);
        }
    }
}
