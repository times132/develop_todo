package com.jejujg.payload.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LoginRequest {

    private String username;
    private String password;

}
