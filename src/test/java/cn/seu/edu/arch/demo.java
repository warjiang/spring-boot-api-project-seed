package cn.seu.edu.arch;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.junit.Test;

import java.security.Key;
import java.util.Date;

/**
 * Author by dingwj@seu.edu.cn on 2017/7/19.
 */
public class demo {
    @Test
    public void testJWT(){
        Key key = MacProvider.generateKey();

        String compactJws = Jwts.builder()
                .setSubject("Joe")
                .claim("username", "warjiang")
                .claim("roleNames", "war1096409085")
                .setExpiration(new Date())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        System.out.println(compactJws);
        //assert Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getSubject().equals("Joe");
    }
}
