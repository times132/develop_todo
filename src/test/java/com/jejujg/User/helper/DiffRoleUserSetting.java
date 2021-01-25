package com.jejujg.User.helper;

import com.jejujg.model.User;
import com.jejujg.repository.RoleRepository;
import com.jejujg.repository.UserRepository;
import com.jejujg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DiffRoleUserSetting extends IntegrationSetting {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected UserTestHelper userTestHelper;

    protected User GUEST1;
    protected User USER1;

    protected void prepareTwoUser() {
//        userService.clearUser();
        userRepository.deleteAll();
        this.userTestHelper = new UserTestHelper(userRepository, roleRepository, passwordEncoder);
        this.GUEST1 = this.userTestHelper.createUser("guest1", "ROLE_GUEST");
        this.USER1 = this.userTestHelper.createUser("user1", "ROLE_USER");
    }
}
