package cn.seu.edu.arch.jwt;

import cn.seu.edu.arch.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/19.
 */

@Slf4j
@Component
public class JsonWebTokenUtility {
    @Value("${app.name}")
    private String APP_NAME;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.header}")
    private String AUTH_HEADER;

    @Autowired
    UserService userService;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private long getCurrentTimeMillis() {
        return DateTime.now().getMillis();
    }

    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(getCurrentTimeMillis() + this.EXPIRES_IN * 1000);
    }


    // 生成JWT token
    public String generateJSONWebToken(String userId) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(userId)
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .compact();
    }

    // 从jwt中解析出token
    public String parseAndGetUserId(String token) {
        String userId = null;
        if (token == null || token.length() == 0) {
            log.info("no jwt in http header");
            return userId;
        }
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            userId = claims.getSubject();
        } catch (JwtException ex) {
            log.error(ex.getMessage());
        }
        return userId;
    }

    // 从http的请求头AUTH_HEADER中取出 jwt
    public String getToken(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }
}
