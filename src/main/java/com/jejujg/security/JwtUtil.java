package com.jejujg.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class JwtUtil {
    @Value("${jwt.accessTokenExpiration}")
    public long accessTokenExpiration; // 10분
    @Value("${jwt.refreshTokenExpiration}")
    public long refreshTokenExpiration; // 2일
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    // 토큰 생성
    public String create(String username, long expireTime){
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String createAccessToken(String username){
        return create(username, accessTokenExpiration);
    }

    public String createRefreshToken(String username){
        return create(username, refreshTokenExpiration);
    }

    public Date getTokenExpired(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return claims.get("username", String.class);
    }

    public boolean validateToken(String token, UserDetails userDetails){
        logger.info("엑세스 토큰: " + token);
        try {
            String username = getUsername(token);

            return (username.equals(userDetails.getUsername()) && ! Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration().before(new Date()));
        }catch (SignatureException e){
            logger.error("서명을 확인 할 수 없는 토큰입니다.");
        }catch (MalformedJwtException e){
            logger.error("올바른 토큰이 아닙니다.");
        }catch (ExpiredJwtException e){
            logger.error("유효기간이 만료된 토큰입니다.");
        }catch (UnsupportedJwtException e){
            logger.error("지원하지 않는 토큰입니다.");
        }catch (IllegalArgumentException e){
            logger.error("토큰에 값이 없습니다.");
        }
        return false;
    }
}
