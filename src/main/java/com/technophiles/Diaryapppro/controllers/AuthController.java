package com.technophiles.Diaryapppro.controllers;


import com.technophiles.Diaryapppro.controllers.request.LoginRequest;
import com.technophiles.Diaryapppro.controllers.response.AuthToken;
import com.technophiles.Diaryapppro.models.User;
import com.technophiles.Diaryapppro.security.jwt.TokenProvider;
import com.technophiles.Diaryapppro.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/diaryApp/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateJWTToken(authentication);
        User user = userService.findUserByEmail(loginRequest.getEmail());
        return new ResponseEntity<>(new AuthToken(token, user.getEmail()), HttpStatus.OK);
    }
}
