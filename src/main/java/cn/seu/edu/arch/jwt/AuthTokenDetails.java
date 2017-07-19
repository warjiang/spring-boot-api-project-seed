package cn.seu.edu.arch.jwt;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/19.
 */

@Data
public class AuthTokenDetails {
    private Long id;// 用户ID
    private String username;// 用户登录名
    private String ip;// 用户IP

    private List<String> roleNames;
    private Date expirationDate;
}
