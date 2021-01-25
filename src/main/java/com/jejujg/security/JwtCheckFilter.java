package com.jejujg.security;

import com.jejujg.service.CookieUtil;
import com.jejujg.service.RedisUtil;
import com.jejujg.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtCheckFilter extends OncePerRequestFilter {

    private final JwtUtil JWTUtil;
    private final CookieUtil cookieUtil;
    private final UserService userService;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie jwtToken = cookieUtil.getCookie(request, "accessToken");

        String username = null;
        String jwt = null;
        String refreshJwt = null;
        String refreshUsername = null;

        try {
            if (jwtToken != null){ // accessToken이 있으면 토큰에서 사용자 이름을 불러옴
                jwt = jwtToken.getValue();
                username = JWTUtil.getUsername(jwt);
            }
            if (username != null){ // accessToken이 유효하면 사용자 정보로 userdetails 생성
                UserDetails userDetails = userService.loadUserByUsername(username);

                if (JWTUtil.validateToken(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (ExpiredJwtException | NullPointerException e) { // accessToken의 기간이 만료된 경우 쿠키에서 refreshToken을 읽어옴
            Cookie refreshToken = cookieUtil.getCookie(request, "refreshToken");
            if (refreshToken != null){
                refreshJwt = refreshToken.getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (refreshJwt != null) { // refreshToken에서 유저 정보를 읽어 accessToken을 새로 생성해서 전달
                refreshUsername = redisUtil.getData(refreshJwt);

                // refreshToken 만료 기간이 일정 이하로 남았을 때 redis에서 삭제하고 재발급
                if (JWTUtil.getTokenExpired(refreshJwt).getTime() - System.currentTimeMillis() < 21600000){ // 6시간 미만으로 남았을 때
                    String newRefreshToken = JWTUtil.createRefreshToken(refreshUsername);

                    Cookie newRefreshCookie = cookieUtil.createCookie("refreshToken", newRefreshToken, JWTUtil.refreshTokenExpiration / 1000); // 2일
                    response.addCookie(newRefreshCookie);

                    redisUtil.deleteData(refreshJwt);
                    redisUtil.setDataExpire(newRefreshToken, refreshUsername, JWTUtil.refreshTokenExpiration / 1000);
                }

                // redis에 저장된 유저와 토큰의 저장된 유저가 같은 경우 accessToken을 재발급, 세션 유지
                if (refreshUsername.equals(JWTUtil.getUsername(refreshJwt))) {
                    UserDetails userDetails = userService.loadUserByUsername(refreshUsername);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    String newAccessToken = JWTUtil.createAccessToken(refreshUsername);

                    Cookie newAccessCookie = cookieUtil.createCookie("accessToken", newAccessToken, JWTUtil.accessTokenExpiration / 1000 * 6 * 3); // 3시간
                    response.addCookie(newAccessCookie);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }


}
