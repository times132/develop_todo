package com.jejujg.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SignupRequest {

    private String username;
    private String password;
    private String name;
    @Email
    private String email;
    private String nickname;
    private String phone;

    public String getPassword() {
        return new BCryptPasswordEncoder().encode(password);
    }
}
