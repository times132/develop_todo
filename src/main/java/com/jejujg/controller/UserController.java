package com.jejujg.controller;

import com.jejujg.payload.request.LoginRequest;
import com.jejujg.payload.request.SignupRequest;
import com.jejujg.payload.response.UserResponse;
import com.jejujg.security.CustomUserDetails;
import com.jejujg.security.JwtUtil;
import com.jejujg.service.CookieUtil;
import com.jejujg.service.RedisUtil;
import com.jejujg.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public Long signup(@RequestBody SignupRequest request){
        return userService.save(request);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
////        CustomUserDetails authentication = (CustomUserDetails) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())).getPrincipal();
//        String accessJwt = jwtUtil.createAccessToken(loginRequest.getUsername());
//        String refreshJwt = jwtUtil.createRefreshToken(loginRequest.getUsername());
//
//        Cookie accessToken = cookieUtil.createCookie("accessToken", accessJwt, jwtUtil.accessTokenExpiration / 1000 * 6 * 3); // 3시간
//        Cookie refreshToken = cookieUtil.createCookie("refreshToken", refreshJwt, jwtUtil.refreshTokenExpiration / 1000 ); // 2일
//
//        redisUtil.setDataExpire(refreshJwt, loginRequest.getUsername(), jwtUtil.refreshTokenExpiration / 1000); // 2일
//
//        response.addCookie(accessToken);
//        response.addCookie(refreshToken);
//
//        return new ResponseEntity<>(accessJwt, HttpStatus.OK);
//    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse responses){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Cookie accessToken = cookieUtil.getCookie(request, "accessToken");
        Cookie refreshToken = cookieUtil.getCookie(request, "refreshToken");
        String username = null;

        try {
            username = jwtUtil.getUsername(accessToken.getValue());
        } catch (ExpiredJwtException e){
            username = jwtUtil.getUsername(e.getClaims().get("username").toString());
        } catch (NullPointerException e){
            return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
        }

        try { // 토큰이 유효할 때 쿠키를 삭제하고 로그아웃 처리
            if (((CustomUserDetails)auth.getPrincipal()).getUsername().equals(username)){
                redisUtil.deleteData(refreshToken.getValue());

                accessToken.setMaxAge(0);
                accessToken.setPath("/");
                responses.addCookie(accessToken);

                refreshToken.setMaxAge(0);
                refreshToken.setPath("/");
                responses.addCookie(refreshToken);

                new SecurityContextLogoutHandler().logout(request, responses, auth);
            }
        } catch (IllegalArgumentException e){
            logger.error("존재 하지 않는 유저입니다.");
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal CustomUserDetails userDetails){
        UserResponse user = UserResponse.builder()
                .username(userDetails.getUsername())
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .roles(userDetails.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()))
                .build();

        return user;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
}
