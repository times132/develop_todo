package com.jejujg.User.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jejujg.User.helper.Token;
import com.jejujg.payload.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

public class IntegrationSetting {

    @LocalServerPort
    protected int port;

    @Autowired
    protected ObjectMapper objectMapper;

    protected RestTemplate restTemplate = new RestTemplate();

    protected URI uri(String path) throws URISyntaxException {
        return new URI(format("http://localhost:%d%s", port, path));
    }

    protected Token getToken(String username, String password) throws URISyntaxException {
        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
        HttpEntity<LoginRequest> body = new HttpEntity<>(loginRequest);

        ResponseEntity<String> response = restTemplate.exchange(uri("/user/login"), HttpMethod.POST, body, String.class);
        return Token.builder().accessToken(getAccessToken(response)).build();
    }

    protected String getAccessToken(ResponseEntity<String> response) {
        return response.getHeaders().get("Set-Cookie").get(0)
                .substring(12).split(";")[0];
    }
}
