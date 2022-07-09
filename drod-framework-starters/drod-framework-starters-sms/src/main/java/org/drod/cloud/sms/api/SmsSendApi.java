package org.drod.cloud.sms.api;

import org.drod.cloud.sms.bean.SmsResult;
import org.drod.cloud.sms.config.SmsProperties;
import org.drod.cloud.sms.http.SmsHttp;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;

import java.io.IOException;
import java.util.Map;

/**
 * 短信发送 API
 *
 * @author Deng
 * @create: 2022-06-10 10:52
 **/

public class SmsSendApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSendApi.class);

    @Autowired
    private SmsHttp smsHttp;

    private SmsProperties smsProperties;

    public SmsSendApi(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    /**
     * 自定义短信发送
     */
    public SmsResult send(String telephone, String content) throws IOException {
        LOGGER.debug(">>>>>>>>>>> SmsSendApi.send, params: telephone: {}, content:{} ", telephone, content);

        if (null == telephone || null == content) {
            throw new NullPointerException("telephone or content is null");
        }
        Call<ResponseBody> call =
            smsHttp.send(this.smsProperties.getAppId(), telephone, content, this.smsProperties.getAppKey());
        ResponseBody body = call.execute().body();
        return SmsResult.getResult(body.string());
    }

    /**
     * 短信模板发送
     */
    public SmsResult sendTemplete(String telephone, String project, Map<String, String> vars) throws IOException {
        LOGGER.debug(">>>>>>>>>>> SmsSendApi.send, params: telephone: {}, project:{}, vars:{} ", telephone, project,
            vars);

        if (null == telephone || null == project || null == vars) {
            throw new NullPointerException("telphone or project or vars is null");
        }
        String varsString = new ObjectMapper().writeValueAsString(vars);
        Call<ResponseBody> call =
            smsHttp.xSend(this.smsProperties.getAppId(), telephone, project, varsString, this.smsProperties.getAppKey());
        ResponseBody body = call.execute().body();
        return SmsResult.getResult(body.string());
    }

}
