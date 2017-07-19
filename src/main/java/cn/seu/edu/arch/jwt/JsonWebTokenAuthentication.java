package cn.seu.edu.arch.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/19.
 */
public class JsonWebTokenAuthentication extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -6855809445272533821L;
    private UserDetails principal;
    private String jsonWebToken;


    public JsonWebTokenAuthentication(UserDetails principal, String jsonWebToken) {
        super(principal.getAuthorities());
        this.principal = principal;
        this.jsonWebToken = jsonWebToken;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getJsonWebToken() {
        return jsonWebToken;
    }
}
