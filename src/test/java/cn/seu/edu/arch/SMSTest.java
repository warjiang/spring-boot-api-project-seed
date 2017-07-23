package cn.seu.edu.arch;

import cn.seu.edu.arch.service.QcloudSMSService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * Author by dingwj@seu.edu.cn on 2017/7/21.
 */

@Slf4j
public class SMSTest extends Tester{
    @Autowired
    QcloudSMSService qcloudSMSService;

    @Test
    public void sendSMS(){
        try {
            //SmsSingleSenderResult result = qcloudSMSService.send(0, "86", "13770507225", "您注册的验证码：1234", "", "");
            //指定模板单发
            //假设短信模板 id 为 1，模板内容为：测试短信，{1}，{2}，{3}，上学。
            ArrayList<String> params = new ArrayList<>();
            params.add("123456");
            params.add("5");
            //SmsSingleSenderResult result = qcloudSMSService.sendWithParam("86", "13770507225", 11810, params, "", "", "");
            log.info("finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
