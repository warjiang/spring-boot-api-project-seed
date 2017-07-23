package cn.seu.edu.arch.model;

import java.util.ArrayList;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/21.
 */
public class SmsSingleSenderResult {
    /*
    {
        "result": 0,
        "errmsg": "OK",
        "ext": "",
        "sid": "xxxxxxx",
        "fee": 1
    }
    */
    public int result;
    public String errMsg = "";
    public String ext = "";
    public String sid = "";
    public int fee;

    public String toString() {
        return String.format(
                "SmsSingleSenderResult\nresult %d\nerrMsg %s\next %s\nsid %s\nfee %d",
                result, errMsg, ext, sid, fee);
    }
}
