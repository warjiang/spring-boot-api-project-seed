package cn.seu.edu.arch.service;

import cn.seu.edu.arch.model.SmsSingleSenderResult;
import cn.seu.edu.arch.util.SmsSenderUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/21.
 */
@Component
public class QcloudSMSService {
    @Value("${QCloudSMS.APP_ID}")
    private Integer APP_ID;

    @Value("${QCloudSMS.APP_KEY}")
    private String APP_KEY;

    @Value("${QCloudSMS.URL}")
    private String URL;

    @Autowired
    SmsSenderUtil util;

    /**
     * 指定模板单发
     * @param nationCode 国家码，如 86 为中国
     * @param phoneNumber 不带国家码的手机号
     * @param templId 信息内容
     * @param params 模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
     * @param sign 签名，如果填空，系统会使用默认签名
     * @param extend 扩展码，可填空
     * @param ext 服务端原样返回的参数，可填空
     * @return {@link}SmsSingleSenderResult
     * @throws Exception
     */
    public SmsSingleSenderResult sendWithParam(
            String nationCode,
            String phoneNumber,
            int templId,
            ArrayList<String> params,
            String sign,
            String extend,
            String ext) throws Exception {
        if (null == nationCode || 0 == nationCode.length()) {
            nationCode = "86";
        }
        if (null == params) {
            params = new ArrayList<>();
        }
        if (null == sign) {
            sign = "";
        }
        if (null == extend) {
            extend = "";
        }
        if (null == ext) {
            ext = "";
        }

        long random = util.getRandom();
        long curTime = System.currentTimeMillis()/1000;

        JSONObject data = new JSONObject();
        JSONObject tel = new JSONObject();
        tel.put("nationcode", nationCode);
        tel.put("mobile", phoneNumber);

        data.put("tel", tel);
        data.put("sig", util.calculateSigForTempl(APP_KEY, random, curTime, phoneNumber));
        data.put("tpl_id", templId);
        data.put("params", util.smsParamsToJSONArray(params));
        data.put("sign", sign);
        data.put("time", curTime);
        data.put("extend", extend);
        data.put("ext", ext);

        SmsSingleSenderResult result;
        String wholeUrl = String.format("%s?sdkappid=%d&random=%d", URL, APP_ID, random);
        String response = HttpRequest.post(wholeUrl).send(data.toJSONString()).body();
        JSONObject json = (JSONObject) JSON.parse(response);
        result = util.jsonToSmsSingleSenderResult(json);
        return result;
    }


    /**
     * 普通单发短信接口，明确指定内容，如果有多个签名，请在内容中以【】的方式添加到信息内容中，否则系统将使用默认签名
     *
     * @param type        短信类型，0 为普通短信，1 营销短信
     * @param nationCode  国家码，如 86 为中国
     * @param phoneNumber 不带国家码的手机号
     * @param msg         信息内容，必须与申请的模板格式一致，否则将返回错误
     * @param extend      扩展码，可填空
     * @param ext         服务端原样返回的参数，可填空
     * @return {@link}SmsSingleSenderResult
     * @throws Exception
     */
    public SmsSingleSenderResult send(
            int type,
            String nationCode,
            String phoneNumber,
            String msg,
            String extend,
            String ext) throws Exception {

        // 校验 type 类型
        if (0 != type && 1 != type) {
            throw new Exception("type " + type + " error");
        }
        if (null == extend) {
            extend = "";
        }
        if (null == ext) {
            ext = "";
        }

        long random = util.getRandom();
        long curTime = System.currentTimeMillis() / 1000;

        JSONObject data = new JSONObject();
        JSONObject tel = new JSONObject();
        tel.put("nationcode", nationCode);
        tel.put("mobile", phoneNumber);

        data.put("type", type);
        data.put("msg", msg);
        data.put("sig", util.strToHash(String.format(
                "appkey=%s&random=%d&time=%d&mobile=%s",
                APP_KEY, random, curTime, phoneNumber)));
        data.put("tel", tel);
        data.put("time", curTime);
        data.put("extend", extend);
        data.put("ext", ext);

        SmsSingleSenderResult result;
        String wholeUrl = String.format("%s?sdkappid=%d&random=%d", URL, APP_ID, random);
        String response = HttpRequest.post(wholeUrl).send(data.toJSONString()).body();
        JSONObject json = (JSONObject) JSON.parse(response);
        result = util.jsonToSmsSingleSenderResult(json);
        return result;
    }
}
