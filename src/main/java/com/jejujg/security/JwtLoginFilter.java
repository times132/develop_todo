package com.jejujg.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jejujg.model.User;
import com.jejujg.payload.request.LoginRequest;
import com.jejujg.service.CookieUtil;
import com.jejujg.service.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtLoginFilter.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword(), null
        );
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();
        String accessJwt = jwtUtil.createAccessToken(user.getUsername());
        String refreshJwt = jwtUtil.createRefreshToken(user.getUsername());

        Cookie accessToken = cookieUtil.createCookie("accessToken", accessJwt, jwtUtil.accessTokenExpiration / 1000 * 6 * 3);
        Cookie refreshToken = cookieUtil.createCookie("refreshToken", refreshJwt, jwtUtil.refreshTokenExpiration / 1000);

        redisUtil.setDataExpire(refreshJwt, user.getUsername(), jwtUtil.refreshTokenExpiration / 1000);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.error(failed.getMessage());
        response.sendError(401, "login error");
    }

}
