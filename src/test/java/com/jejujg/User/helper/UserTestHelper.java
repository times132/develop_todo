package com.jejujg.User.helper;

import com.jejujg.model.Role;
import com.jejujg.model.User;
import com.jejujg.model.UserRole;
import com.jejujg.repository.RoleRepository;
import com.jejujg.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@AllArgsConstructor
public class UserTestHelper {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public User createUser(String username) {
        Role role = roleRepository.findByName(UserRole.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("권한이 설정 안됬습니다."));

        User user = User.builder()
                .username(username)
                .name(username+"123")
                .password(passwordEncoder.encode("1234"))
                .email(username+"@naver.com")
                .nickname(username+"321")
                .phone("010-1111-2222")
                .roles(Collections.singleton(role))
                .build();

        return userRepository.save(user);
    }

    public User createUser(String username, String authority) {
        Role role = roleRepository.findByName(UserRole.valueOf(authority))
                .orElseThrow(() -> new IllegalArgumentException("권한이 설정 안됬습니다."));

        User user = User.builder()
                .username(username)
                .name(username+"123")
                .password(passwordEncoder.encode("1234"))
                .email(username+"@naver.com")
                .nickname(username+"321")
                .phone("010-1111-2222")
                .roles(Collections.singleton(role))
                .build();

        return userRepository.save(user);
    }
}
