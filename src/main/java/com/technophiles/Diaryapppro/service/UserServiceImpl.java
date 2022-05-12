package com.technophiles.Diaryapppro.service;

import com.technophiles.Diaryapppro.dtos.UserDto;
import com.technophiles.Diaryapppro.exception.DiaryApplicationException;
import com.technophiles.Diaryapppro.exception.UserNotFoundException;
import com.technophiles.Diaryapppro.models.Diary;
import com.technophiles.Diaryapppro.models.User;
import com.technophiles.Diaryapppro.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Optional;


@Service
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public UserDto createAccount(String email, String password) throws DiaryApplicationException {

       Optional<User> userOptional = userRepository.findUserByEmail(email);
       if(userOptional.isEmpty()){
           User user = new User(email, password);
           User savedUser =  userRepository.save(user);
            return mapper.map(savedUser,UserDto.class);
       }
        throw new DiaryApplicationException("email already exist");
    }

    @Override
    public Diary addDiary(@NotNull Long id,@NotNull Diary diary) throws DiaryApplicationException {
        User user = userRepository.findById(id).orElseThrow(()-> new DiaryApplicationException("user does not exist"));
        user.addDiary(diary);
         userRepository.save(user);
        return diary;
    }

    @Override
    public User findById(Long userId) throws DiaryApplicationException {
        return userRepository.findById(userId).orElseThrow(()-> new DiaryApplicationException("user does not exist"));
    }

    @Override
    public boolean deleteUser(User user) {
         userRepository.delete(user);
         return true;
    }
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(()-> new  UserNotFoundException("user not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), new ArrayList<>());
    }
}
