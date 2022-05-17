package com.technophiles.Diaryapppro.config;

import com.technophiles.Diaryapppro.models.Role;
import com.technophiles.Diaryapppro.models.RoleType;
import com.technophiles.Diaryapppro.models.User;
import com.technophiles.Diaryapppro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetUpDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(userRepository.findUserByEmail("adminuser@gmail.com").isEmpty()){
            User user = new User("adminuser@gmail.com",passwordEncoder.encode("newPassword"));
            user.addRole(new Role(RoleType.ROLE_ADMIN));
            userRepository.save(user);
        }
    }

}

