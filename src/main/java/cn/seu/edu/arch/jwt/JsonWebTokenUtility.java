package cn.seu.edu.arch.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/19.
 */


public class JsonWebTokenUtility {
    private final Logger logger = LoggerFactory.getLogger(JsonWebTokenUtility.class);
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    private String encodedKey = "YXJjaGFwcA==";
    private Key secretKey = deserializeKey(encodedKey);

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public String getEncodedKey() {
        return encodedKey;
    }

    public Key getSecretKey() {
        return secretKey;
    }


    private Key deserializeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        Key key = new SecretKeySpec(decodedKey, getSignatureAlgorithm().getJcaName());
        return key;
    }

    private String serializeKey(Key key) {
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        return encodedKey;
    }

    public String createJsonWebToken(AuthTokenDetails authTokenDetails) {
        String token = Jwts.builder()
                .setSubject(authTokenDetails.getId().toString())
                .claim("username", authTokenDetails.getUsername())
                .claim("roleNames", authTokenDetails.getRoleNames())
                .setExpiration(authTokenDetails.getExpirationDate())
                .signWith(getSignatureAlgorithm(), getSecretKey())
                .compact();
        return token;
    }

    public AuthTokenDetails parseAndValidate(String token) {
        AuthTokenDetails authTokenDetails = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            String username = (String) claims.get("username");
            List<String> roleNames = (List) claims.get("roleNames");
            Date expirationDate = claims.getExpiration();

            authTokenDetails = new AuthTokenDetails();
            authTokenDetails.setId(Long.valueOf(userId));
            authTokenDetails.setUsername(username);
            authTokenDetails.setRoleNames(roleNames);
            authTokenDetails.setExpirationDate(expirationDate);
        } catch (JwtException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return authTokenDetails;
    }
}
