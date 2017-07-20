package cn.seu.edu.arch.configurer;

import cn.seu.edu.arch.core.Result;
import cn.seu.edu.arch.core.ResultCode;
import cn.seu.edu.arch.jwt.JsonWebTokenUtility;
import cn.seu.edu.arch.model.User;
import cn.seu.edu.arch.service.UserService;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author by dingwenjiang on 2017/7/20.
 */
@Slf4j
@Component
public class JWTInterceptor extends HandlerInterceptorAdapter {
    @Value("${authorizeURLLists.studentURLList}")
    @Getter
    private String[] studentURLList;

    @Value("${authorizeURLLists.teacherURLList}")
    @Getter
    private String[] teacherURLList;

    @Value("${authorizeURLLists.adminURLList}")
    @Getter
    private String[] adminURLList;

    @Value("${authorizeURLLists.publicURLList}")
    @Getter
    private String[] publicURLList;

    @Autowired
    private JsonWebTokenUtility tokenUtility;

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //验证签名
        boolean pass = validateJWT(request);
        if (pass) {
            return true;
        } else {
            log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}",
                    request.getRequestURI(), getIpAddress(request), JSON.toJSONString(request.getParameterMap()));

            Result result = new Result();
            result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名认证失败");
            responseResult(response, result);
            return false;
        }
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private boolean validateJWT(HttpServletRequest request) {
        //boolean isTokenValid = tokenUtility.parseAndValidate(tokenUtility.getToken(request));
        String userId = tokenUtility.parseAndGetUserId(tokenUtility.getToken(request));
        if (userId == null) {
            return false;
        }
        User user = userService.findById(Integer.valueOf(userId));
        if (user == null){
            return false;
        }

        String targetUrl = request.getRequestURI();
        String type = "Default";
        String roleStr = user.getRole();
        // check for student permission
        for (String aStudentURLList : studentURLList) {
            if (targetUrl.equals(aStudentURLList)) {
                type = "S";
                break;
            }
        }
        if (type.equals("S")) {
            return roleStr.contains(type);
        }

        // check for teacher permission
        for (String aTeacherURLList : teacherURLList) {
            if (targetUrl.equals(aTeacherURLList)) {
                type = "T";
                break;
            }
        }
        if (type.equals("T")) {
            return roleStr.contains(type);
        }

        // check for admin permission
        for (String aAdminURLList : adminURLList) {
            if (targetUrl.equals(aAdminURLList)) {
                type = "A";
                break;
            }
        }
        if (type.equals("A")) {
            return roleStr.contains(type);
        }

        return false;

    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }
}
