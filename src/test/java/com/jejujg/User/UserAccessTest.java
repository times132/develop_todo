package com.jejujg.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jejujg.controller.HomeController;
import com.jejujg.controller.UserController;
import com.jejujg.model.Role;
import com.jejujg.model.User;
import com.jejujg.model.UserRole;
import com.jejujg.security.CustomUserDetails;
import com.jejujg.security.JwtUtil;
import com.jejujg.service.CookieUtil;
import com.jejujg.service.RedisUtil;
import com.jejujg.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@WebMvcTest({HomeController.class, UserController.class})
public class UserAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CookieUtil cookieUtil;

    @MockBean
    private RedisUtil redisUtil;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    CustomUserDetails user1() {
        return CustomUserDetails.create(User.builder()
                .email("user@naver.com")
                .name("Park")
                .password(passwordEncoder.encode("123"))
                .username("times132")
                .roles(Collections.singleton(Role.builder().name(UserRole.ROLE_USER).build()))
                .build());
    }

    CustomUserDetails admin() {
        return CustomUserDetails.create(User.builder()
                .email("admin@naver.com")
                .name("Kim")
                .password(passwordEncoder.encode("1234"))
                .username("admin")
                .roles(Collections.singleton(Role.builder().name(UserRole.ROLE_ADMIN).build()))
                .build());
    }

    @DisplayName("1. user가 홈화면 접속")
    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void test_1() throws Exception {
        String res = mockMvc.perform(get("/api/home"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals("127.0.0.1", res);
    }

    @DisplayName("2. user가 admin 페이지 접속은 안됨")
    @Test
    void test_2() throws Exception {
        mockMvc.perform(get("/user/admin").with(user(user1())))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("3. admin은 admin 페이지 접속 가능")
    @Test
    void test_3() throws Exception {
         String result = mockMvc.perform(get("/user/admin").with(user(admin())))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
         assertEquals("admin", result);
    }
}
