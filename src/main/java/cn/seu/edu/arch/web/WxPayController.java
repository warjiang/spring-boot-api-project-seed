package cn.seu.edu.arch.web;

import cn.seu.edu.arch.core.Result;
import cn.seu.edu.arch.core.ResultGenerator;
import cn.seu.edu.arch.jwt.JsonWebTokenUtility;
import cn.seu.edu.arch.model.PayInfo;
import cn.seu.edu.arch.model.User;
import cn.seu.edu.arch.service.impl.UserServiceImpl;
import cn.seu.edu.arch.util.CommonUtil;
import cn.seu.edu.arch.util.RandomUtils;
import cn.seu.edu.arch.util.TimeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/21.
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WxPayController {
    @Value("${wxpay.APP_ID}")
    private String APP_ID;

    @Value("${wxpay.APP_KEY}")
    private String APP_KEY;

    @Value("${wxpay.APP_SECRET}")
    private String APP_SECRET;

    @Value("${wxpay.MCH_ID}")
    private String MCH_ID;

    @Value("${wxpay.TIME_FORMAT}")
    private String TIME_FORMAT;

    @Value("${wxpay.TIME_EXPIRE}")
    private Integer TIME_EXPIRE;

    @Value("${wxpay.URL_UNIFIED_ORDER}")
    private String URL_UNIFIED_ORDER;

    @Value("${wxpay.URL_NOTIFY}")
    private String URL_NOTIFY;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JsonWebTokenUtility tokenService;

    @PostMapping("/registerByCode")
    public Result registerByCode(@RequestParam String code,
                                 @RequestParam String username,
                                 @RequestParam String avatarurl) {
        String openId = getOpenId(code);
        User user = userService.findBy("openid", openId);
        log.info(openId.toUpperCase());
        HashMap<String, String> ret = new HashMap<>();
        if (user == null) {
            user = new User();
            user.setOpenid(openId);
            user.setAvatar(avatarurl);
            user.setUsername(username);
            userService.save(user);
            ret.put("registInfo", "用户注册成功");
        } else {
            ret.put("registInfo", "请勿重复注册");
        }

        String token = tokenService.generateJSONWebToken(String.valueOf(user.getUserId()));
        ret.put("token", token);
        ret.put("userId", String.valueOf(user.getUserId()));
        return ResultGenerator.genSuccessResult(ret);
    }

    @GetMapping("/prePay")
    public Result prePay(@RequestParam String code, HttpServletRequest request) {
        String openId = getOpenId(code);
        log.error(openId);
        String randomNonceStr = RandomUtils.generateMixString(32);
        String clientIP = CommonUtil.getClientIp(request);
        String prepayId = unifiedOrder(openId, clientIP, randomNonceStr);
        log.error("prepayId: ", prepayId);
        // TODO 业务逻辑处理
        return ResultGenerator.genSuccessResult();
    }

    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        String response = HttpRequest.get(url).body();
        JSONObject obj = (JSONObject) JSON.parse(response);
        if (obj.get("errcode") != null) {
            log.error("getOpenId returns errcode: " + obj.get("errcode"));
            return "";
        } else {
            return obj.get("openid").toString();
        }
    }

    /**
     * 调用统一下单接口
     *
     * @param openId
     */
    private String unifiedOrder(String openId, String clientIP, String randomNonceStr) {
        PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr);
        try {
            String md5 = getSign(payInfo);
            payInfo.setSign(md5);
            String xml = CommonUtil.payInfoToXML(payInfo);
            xml = xml.replace("__", "_").replace("<![CDATA[1]]>", "1");
            log.error(xml);
            String response = HttpRequest.post(URL_UNIFIED_ORDER).send(xml).body();
            log.info(response);
            Map<String, String> result = CommonUtil.parseXml(response);

            String return_code = result.get("return_code");
            if (return_code != null && return_code.length() != 0 && return_code.equals("SUCCESS")) {
                String return_msg = result.get("return_msg");
                if (return_msg != null && return_msg.length() != 0 && !return_msg.equals("OK")) {
                    //log.error("统一下单错误！");
                    return "";
                }
                return result.get("prepay_id");
            } else
                return "";

        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.getMessage());
        }
        return "";
    }

    private PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr) {
        Date date = new Date();
        String timeStart = TimeUtils.getFormatTime(date, TIME_FORMAT);
        String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, TIME_EXPIRE), TIME_FORMAT);
        String randomOrderId = CommonUtil.getRandomOrderId();

        PayInfo payInfo = new PayInfo();
        payInfo.setAppid(APP_ID);
        payInfo.setMch_id(MCH_ID);
        payInfo.setDevice_info("WEB");
        payInfo.setNonce_str(randomNonceStr);
        payInfo.setSign_type("MD5");  //默认即为MD5
        payInfo.setBody("JSAPI支付测试");
        payInfo.setAttach("支付测试4luluteam");
        payInfo.setOut_trade_no(randomOrderId);
        payInfo.setTotal_fee(1);
        payInfo.setSpbill_create_ip(clientIP);
        payInfo.setTime_start(timeStart);
        payInfo.setTime_expire(timeExpire);
        payInfo.setNotify_url(URL_NOTIFY);
        payInfo.setTrade_type("JSAPI");
        payInfo.setLimit_pay("no_credit");
        payInfo.setOpenid(openId);

        return payInfo;
    }

    private String getSign(PayInfo payInfo) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("appid=" + payInfo.getAppid())
                .append("&attach=" + payInfo.getAttach())
                .append("&body=" + payInfo.getBody())
                .append("&device_info=" + payInfo.getDevice_info())
                .append("&limit_pay=" + payInfo.getLimit_pay())
                .append("&mch_id=" + payInfo.getMch_id())
                .append("&nonce_str=" + payInfo.getNonce_str())
                .append("&notify_url=" + payInfo.getNotify_url())
                .append("&openid=" + payInfo.getOpenid())
                .append("&out_trade_no=" + payInfo.getOut_trade_no())
                .append("&sign_type=" + payInfo.getSign_type())
                .append("&spbill_create_ip=" + payInfo.getSpbill_create_ip())
                .append("&time_expire=" + payInfo.getTime_expire())
                .append("&time_start=" + payInfo.getTime_start())
                .append("&total_fee=" + payInfo.getTotal_fee())
                .append("&trade_type=" + payInfo.getTrade_type());
        //.append("&key=" + APP_KEY);

        log.error("排序后的拼接参数：" + sb.toString());

        return CommonUtil.getMD5(sb.toString().trim()).toUpperCase();
    }
}
