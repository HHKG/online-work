package com.hhk.yebserver.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * UserDetails来自于spring security
     * 根据用户信息生成token
     * */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 根据荷载生成jwt token
     * @param claims
     * @return
     */
    private String generateToken(Map<String,Object> claims){
        // jwt 构建
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    /**
     * token失效时间
     * @return
     */
    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis()+ expiration*1000);
    }

    /**
     * 根据token获取登录用户用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token){
        String username;
       try {
           Claims claims = getClaimsFromToken(token);
           username = claims.getSubject();
       }catch (Exception e){
           username = null;
       }
       return username;
    }

    /**
     * 跟据token或荷载
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token){
       Claims claims= null;
               try {
                    claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
               }catch (Exception e){
                   claims=null;
               }
       return claims;
    }

    /**
     * 验证token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername())&& !isTokenExpired(token);

    }

    /**
     * 判断token是否失效
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token){
       Date expiredDate = getExpiredDateFromToken(token);
       return expiredDate.before(new Date());
    }

    /**
     * 获取token的失效时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token){
       Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * token是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }
    
}
