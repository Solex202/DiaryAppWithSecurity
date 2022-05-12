package com.technophiles.Diaryapppro.service;

import com.technophiles.Diaryapppro.dtos.UserDto;
import com.technophiles.Diaryapppro.exception.DiaryApplicationException;
import com.technophiles.Diaryapppro.models.User;
import com.technophiles.Diaryapppro.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceConcreteTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

//    @Test
//    void testThatCanDeleteUser(){
//        Long id = 1L;
//        User user = userRepository.findById(id).orElse(null);
//        userService.deleteUser(user);
//        User fromDatabase = userRepository.findById(id).orElse(null);
//        assertThat(fromDatabase).isNull();
//    }

    @Test
    void testThatCanDeleteUser2() throws DiaryApplicationException {
        UserDto userDto = userService.createAccount("newemail@gmail.com","password");
        User user = userRepository.findById(userDto.getId()).get();

        userService.deleteUser(user);
       Optional< User> fromDatabase = userRepository.findById(userDto.getId());
        assertThat(fromDatabase).isEqualTo(Optional.empty());
    }
}
