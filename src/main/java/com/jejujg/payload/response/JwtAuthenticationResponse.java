package com.jejujg.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class JwtAuthenticationResponse {

    private String accessToken;

//    @Builder
//    public JwtAuthenticationResponse(String accessToken) {
//        this.accessToken = accessToken;
//    }
}
