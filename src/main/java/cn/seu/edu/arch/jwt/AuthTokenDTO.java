package cn.seu.edu.arch.jwt;

import lombok.Data;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/20.
 */
@Data
public class AuthTokenDTO {
    private String token;
    private Integer userId;
}
