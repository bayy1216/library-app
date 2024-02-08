package com.group.libraryapp.core.jwt;

import com.group.libraryapp.domain.type.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;


    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 30;  // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 30;  // 30일
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String IS_ACCESS_TOKEN = "isAccessToken";

    public String parseHeader(String header, boolean isJwt){
        String prefix = isJwt ? "Bearer " : "Basic ";
        if(header == null || header.isEmpty()){
            throw new IllegalArgumentException("Authorization 헤더가 없습니다.");
        } else if(!header.startsWith(prefix)){
            throw new IllegalArgumentException("Authorization 올바르지 않습니다.");
        } else if(header.split(" ").length != 2){
            throw new IllegalArgumentException("Authorization 올바르지 않습니다.");
        }else {
            return header.split(" ")[1];
        }
    }

    public JwtToken createToken(UserAuth userAuth){
        String accessToken = generateToken(userAuth, true);
        String refreshToken = generateToken(userAuth, false);
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * [validateToken] 이후 호출하는 메서드.
     * UserAuth를 추출한다.
     */
    public UserAuth extractUserAuth(String token){
        Claims claims = extractClaims(token);
        return UserAuth.builder()
                .id(claims.get("id", Long.class))
                .type(UserType.valueOf(claims.get("type", String.class)))
                .build();
    }

    /**
     * Jwt가 유효한지 검사하는 메서드.
     * 만료시간, 토큰의 유효성을 검사한다.
     */
    public boolean validateToken(String token){
        try{
            Claims claims = extractClaims(token);
            return !claims.getExpiration().before(new Date());
        }catch (Exception e){//JwtException, ExpiredJwtException, NullPointerException
            return false;
        }
    }

    /**
     * [validateToken] 이후 호출하는 메서드.
     * refreshToken을 통해, accessToken을 재발급하는 메서드.
     * refreshToken의 유효성을 검사하고, isAccessToken이 true일때만 accessToken을 재발급한다.
     */
    public String reissueAccessToken(String refreshToken){
        Claims claims = extractClaims(refreshToken);
        if(claims.get(IS_ACCESS_TOKEN, Boolean.class)){
            throw new JwtException("RefreshToken이 유효하지 않습니다.");
        }
        UserAuth userAuth = UserAuth.builder()
                .id(claims.get(ID, Long.class))
                .type(UserType.valueOf(claims.get(TYPE, String.class)))
                .build();
        return generateToken(userAuth, true);

    }



    /**
     * Jwt 토큰생성
     * [userAuth]를 통해, id, type, [isAccessToken]을 claim으로 설정하여 토큰을 생성한다.
     * accessToken과 refreshToken의 다른점은 만료시간과, isAccessToken이다.
     */
    private String generateToken(UserAuth userAuth, boolean isAccessToken){
        Key secretKey = generateKey();
        long expireTime = isAccessToken ? ACCESS_TOKEN_EXPIRE_TIME : REFRESH_TOKEN_EXPIRE_TIME;
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        return Jwts.builder()
                .claim(ID, userAuth.getId())
                .claim(TYPE, userAuth.getType())
                .claim(IS_ACCESS_TOKEN, isAccessToken)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    /**
     *HS256방식의 키를 생성한다.
     */
    private Key generateKey(){
        return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    private Claims extractClaims(String token){
        return Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token).getBody();
    }
}
