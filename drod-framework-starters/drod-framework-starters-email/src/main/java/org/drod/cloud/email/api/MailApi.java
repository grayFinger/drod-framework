package org.drod.cloud.email.api;



import org.apache.commons.lang3.ObjectUtils;
import org.drod.cloud.email.bean.MailDTO;
import org.drod.cloud.email.bean.MailResult;
import org.drod.cloud.email.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;

/**
 * 邮件 API
 *
 * @author Deng
 * 2022-07-01 16:52
 **/
public class MailApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailApi.class);

    private MailService mailService;

    public MailApi(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * 自定义邮件发送
     */
    public MailResult send(MailDTO mailDTO) throws MessagingException {
        LOGGER.debug(">>>>>>>>>>> MailApi.send, params: MailDTO: {},", mailDTO);

        if (ObjectUtils.isEmpty(mailDTO.getTo())){
            throw new NullPointerException("String[] to is null");
        }else if (ObjectUtils.isEmpty(mailDTO.getContent())) {
            throw new NullPointerException("String  content is null");
        }else{
            mailService.sendAttachmentsMails(mailDTO);
            return MailResult.getSuccess("Success","200","200","发送成功");
        }
    }
}
