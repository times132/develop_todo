package com.jejujg.User;

import com.jejujg.User.helper.UserAdminSetting;
import com.jejujg.payload.request.LoginRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtLoginTest extends UserAdminSetting {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    private URI uri(String path) throws URISyntaxException{
        return new URI(format("http://localhost:%d%s", port, path));
    }

    @BeforeEach
    void before() {
        prepareUserAdmin();
    }

    @DisplayName("1. 로그인 성공")
    @Test
    void test_1() throws URISyntaxException {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("user1")
                .password("1234").build();
        HttpEntity<LoginRequest> body = new HttpEntity<>(loginRequest);
        ResponseEntity<String> response = restTemplate.exchange(uri("/user/login"), HttpMethod.POST, body, String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @DisplayName("2. 비밀번호가 틀렸을 때")
    @Test
    void test_2() throws URISyntaxException {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("times132")
                .password("12345").build();
        HttpEntity<LoginRequest> body = new HttpEntity<>(loginRequest);

        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.exchange(uri("/user/login"), HttpMethod.POST, body, String.class);
        });
    }
}
