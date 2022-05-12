package com.technophiles.Diaryapppro.controllers;

import com.technophiles.Diaryapppro.controllers.response.ApiResponse;
import com.technophiles.Diaryapppro.dtos.UserDto;
import com.technophiles.Diaryapppro.exception.DiaryApplicationException;
import com.technophiles.Diaryapppro.models.Diary;
import com.technophiles.Diaryapppro.models.User;
import com.technophiles.Diaryapppro.service.DiaryService;
import com.technophiles.Diaryapppro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v3/diaryApp/diaries")
public class DiaryController {
    private DiaryService diaryService;
    private UserService userService;

    @Autowired
    public DiaryController(DiaryService diaryService, UserService userService) {
        this.diaryService = diaryService;
        this.userService = userService;
    }

    @PostMapping("/create/{userId}")
    private ResponseEntity<?> createDiary(@Valid @NotNull @NotBlank @PathVariable("userId") String userId,@Valid @NotNull @NotBlank @RequestParam String title){
        try {
            User user = userService.findById(Long.valueOf(userId));
            Diary diary = diaryService.createDiary(title,user);
            Diary savedDiary = userService.addDiary(Long.valueOf(userId), diary);
            ApiResponse apiResponse = ApiResponse.builder()
                    .payload(savedDiary)
                    .isSuccessful(true)
                    .message("diary added successfully")
                    .statusCode(201)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

        } catch (DiaryApplicationException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .isSuccessful(false)
                    .statusCode(404)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
