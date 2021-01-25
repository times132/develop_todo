package com.jejujg.payload.response;


import com.jejujg.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserResponse {

    private String email;
    private String username;
    private String name;
    private Set<UserRole> roles;
    private String nickname;
    private String phone;

//    public UserResponse toDto(User user){
//        return UserResponse.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .username(user.getUsername())
//                .name(user.getName())
//                .roles(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()))
//                .build();
//    }
}
