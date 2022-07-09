package org.drod.cloud.sms.http;



import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import org.drod.cloud.sms.bean.SmsTemplateResult;
import org.springframework.stereotype.Component;
import retrofit2.http.*;

@RetrofitClient(baseUrl = "https://api-v4.mysubmail.com/")
@Component
public interface SmsTemplateHttp {

    /**
     * 获取模板
     * @param appid 必填
     *          在 SUBMAIL 应用集成中创建的短信应用 ID
     * @param templateId 可选
     *                    模板ID（要获取单个模板，请将在此参数中提交该模板 ID。为空则获取最新的 1000 个短信模板）
     * @param offset  可选    默认0
     *                数据偏移指针 （默认值为0，默认返回最近50个模板信息。offset=1时，查询第51-100个模板，offset=2时，查询第101-150个模板，以此类推。）
     * @param signature 应用密匙或数字签名 (授权相关)
     *
     * @return Call<ResponseBody>
     * @author deng 2022-06-28
     */
    @GET("sms/template")
    SmsTemplateResult getTemplate(@Query("appid") String appid, @Query("template_id") String templateId,
                                  @Query("offset") int offset, @Query("signature")  String signature);


    /**
     * 创建模板
     * @param appid 必填
     *              在 SUBMAIL 应用集成中创建的短信应用 ID
     * @param smsTitle 可选
     *                  模板标题 （创建模板时可以在此参数中提交当前模板的标题，作为模板标记）
     * @param smsSignature 必填
     *                      短信模板签名 (请使用您的公司名或应用、APP、网站名作为您的短信签名，请将签名字数控制在 2-10 个字符以内，并使用全角大括号“【”和“】”包括，如:“【SUBMAIL】”)
     * @param smsContent   必需
     *                      短信正文 (提交短信模板正文，请将模板正文字数控制在 500 个字符以内。可使用文本变量
     * @param signature  必填
     *                   应用密匙或数字签名
     * @return Call<ResponseBody>
     * @author deng 2022-06-28
     */
    @Headers({"Content_Type:application/json", "charset:UTF-8"})
    @FormUrlEncoded
    @POST("sms/template")
    SmsTemplateResult postTemplate(@Field("appid") String appid, @Field("sms_title") String smsTitle,
                                    @Field("sms_signature") String smsSignature, @Field("sms_content")  String smsContent,
                                    @Field("signature")  String signature);


    /**
     *  更新模板
     * @param appid 必填
     *              在 SUBMAIL 应用集成中创建的短信应用 ID
     * @param templateId 必填
     *                    需要更新的模板 ID（在 SUBMAIL > sms >项目中查看您所创建的短信模版 ID）
     * @param smsTitle   可选
     *                    模板标题
     * @param smsSignature 必填
     *                      短信模板签名 (请使用您的公司名或应用、APP、网站名作为您的短信签名，请将签名字数控制在2-15个字符以内，并使用全角大括号“【”和“】”包括，如:“【SUBMAIL】”)
     * @param smsContent 必填
     *                    短信正文
     * @param signature  必填
     *                   应用密匙或数字签名
     * @return Call<ResponseBody>
     * @author deng 2022-06-28
     */
    @Headers({"Content_Type:application/json", "charset:UTF-8"})
    @FormUrlEncoded
    @PUT("sms/template")
    SmsTemplateResult putTemplate(@Field("appid") String appid,@Field("template_id") String templateId,
                                   @Field("sms_title") String smsTitle, @Field("sms_signature") String smsSignature,
                                   @Field("sms_content")  String smsContent,@Field("signature")  String signature);


    /**
     * 删除模板
     * @param appid 必填
     *              在 SUBMAIL 应用集成中创建的短信应用 ID
     * @param templateId 可选
     *                    需要更新的模板 ID（在 SUBMAIL > sms >项目中查看您所创建的短信模版 ID）
     * @param signature  必填
     *                   应用密匙或数字签名
     * @return  Call<ResponseBody>
     * @author deng 2022-06-28
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @HTTP(method = "DELETE", path = "sms/template",hasBody = true)
    @FormUrlEncoded
    SmsTemplateResult deleteTemplate(@Field("appid") String appid,@Field("template_id") String templateId,
                                      @Field("signature")  String signature);
}
