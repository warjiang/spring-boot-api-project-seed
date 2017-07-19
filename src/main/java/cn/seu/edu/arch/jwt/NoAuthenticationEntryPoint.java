package cn.seu.edu.arch.jwt;

import cn.seu.edu.arch.core.Result;
import cn.seu.edu.arch.core.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/19.
 */
public class NoAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //返回json形式的错误信息
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("application/json");

        //RestResp restResp = new RestResp(RestResp.NO_SESSION, );
        Result restResp = new Result();
        restResp.setCode(ResultCode.UNAUTHORIZED);
        restResp.setMessage("没有登录或登录已过期!");

        response.getWriter().println(restResp.toString());
        response.getWriter().flush();
    }
}
