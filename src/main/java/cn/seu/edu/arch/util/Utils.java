package cn.seu.edu.arch.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/20.
 */
@Slf4j
public class Utils {
    public static String getMD5(String str) {
        try {
            return DigestUtils.md5Hex(str);
        } catch (Exception e) {
            //throw new Exception("MD5加密出现错误");
            log.error("MD5加密出现错误");
            return "";
        }
    }
}
