package com.jejujg.User.helper;

import com.jejujg.model.User;
import com.jejujg.repository.RoleRepository;
import com.jejujg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAdminSetting {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected UserTestHelper userTestHelper;

    protected User USER1;
    protected User ADMIN;

    protected void prepareUserAdmin() {
        userRepository.deleteAll();
        this.userTestHelper = new UserTestHelper(userRepository, roleRepository, passwordEncoder);
        this.USER1 = this.userTestHelper.createUser("user1", "ROLE_USER");
        this.ADMIN = this.userTestHelper.createUser("admin", "ROLE_ADMIN");
    }
}
