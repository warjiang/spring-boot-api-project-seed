package cn.seu.edu.arch.model;

import lombok.Data;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/21.
 */
@Data
public class PayInfo {
    private String appid;
    private String mch_id;
    private String device_info; //设备号，小程序传"WEB"
    private String nonce_str;
    private String sign;
    private String sign_type;  //签名类型
    private String body;
    //private String detail;
    private String attach;
    private String out_trade_no;
    private int total_fee;
    private String spbill_create_ip;
    private String time_start;
    private String time_expire;
    private String notify_url;
    private String trade_type; //交易类型,JSAPI
    private String limit_pay;  //指定支付方式，no_credit
    private String openid;
}
