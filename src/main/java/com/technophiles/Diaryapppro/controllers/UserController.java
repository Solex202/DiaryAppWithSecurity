package com.technophiles.Diaryapppro.controllers;


import com.technophiles.Diaryapppro.controllers.response.ApiResponse;
import com.technophiles.Diaryapppro.dtos.UserDto;
import com.technophiles.Diaryapppro.exception.DiaryApplicationException;
import com.technophiles.Diaryapppro.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v3/diaryApp")

public class UserController {

    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/login")
    public String login(){
        return "Welcome to my application";
    }

    @PostMapping("/users/create")
    public ResponseEntity<?> createUser(@RequestParam @Valid @NotNull @NotBlank String email, @RequestParam @Valid @NotBlank @NotNull String password) throws DiaryApplicationException {
//        try{
        password = bCryptPasswordEncoder.encode(password);
            UserDto userDto = userService.createAccount(email,password);
            ApiResponse apiResponse = ApiResponse.builder().payload(userDto).isSuccessful(true).statusCode(201).message("user created successfully").build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
//        }catch(DiaryApplicationException e){
//            ApiResponse apiResponse = ApiResponse.builder().isSuccessful(false).message(e.getMessage()).statusCode(400).build();
//            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
//        }
    }


}
