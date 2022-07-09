package org.drod.cloud.sms.http;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * submail 短信发送 API
 *
 * @author Mist
 */
@RetrofitClient(baseUrl = "https://api-v4.mysubmail.com/")
@Component
public interface SmsHttp {

    /**
     * 发送短信模板
     *
     * @param appId
     *            短信应用 ID
     * @param to
     *            收件人手机号码
     * @param content
     *            短信中的文本
     * @param signature
     *            应用密匙
     * @return Call<ResponseBody>
     */
    @Headers({"Content_Type:application/json", "charset:UTF-8"})
    @FormUrlEncoded
    @POST("sms/send")
    public Call<ResponseBody> send(@Field("appid") String appId, @Field("to") String to,
        @Field("content") String content, @Field("signature") String signature);

    /**
     * 发送短信模板
     *
     * @param appId
     *            短信应用 ID
     * @param to
     *            收件人手机号码
     * @param project
     *            模版 ID
     * @param vars
     *            短信中的文本变量
     * @param signature
     *            应用密匙
     * @return Call<ResponseBody>
     */
    @Headers({"Content_Type:application/json", "charset:UTF-8"})
    @FormUrlEncoded
    @POST("sms/xsend")
    public Call<ResponseBody> xSend(@Field("appid") String appId, @Field("to") String to,
        @Field("project") String project, @Field("vars") String vars, @Field("signature") String signature);
}
