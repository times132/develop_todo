package com.jejujg.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtTokenTest {

    @DisplayName("1. JWT 토큰 생성이 잘됨")
    @Test
    void test_1() throws InterruptedException {
        Claims claims = Jwts.claims();
        claims.put("username", "times132");

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2000))
                .signWith(SignatureAlgorithm.HS512, "test")
                .compact();
        System.out.println(token);

        Thread.sleep(1000);

        claims = Jwts.parser()
                .setSigningKey("test")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("exp: " + claims.getExpiration());
        System.out.println("username: " + claims.get("username"));
        Thread.sleep(1000);

        assertThrows(ExpiredJwtException.class, () ->{
            Jwts.parser()
                    .setSigningKey("test")
                    .parseClaimsJws(token)
                    .getBody();
        });
    }
}
