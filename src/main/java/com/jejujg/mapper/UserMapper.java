package com.jejujg.mapper;

import com.jejujg.model.Role;
import com.jejujg.model.User;
import com.jejujg.payload.request.SignupRequest;
import com.jejujg.payload.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {

    default UserResponse userEntityToDto(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .roles(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()))
                .build();
    }

    User signupDtoToEntity(SignupRequest signupRequest, Set<Role> roles);
}
