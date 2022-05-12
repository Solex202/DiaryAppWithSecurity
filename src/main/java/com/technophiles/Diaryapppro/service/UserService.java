package com.technophiles.Diaryapppro.service;

import com.technophiles.Diaryapppro.dtos.UserDto;
import com.technophiles.Diaryapppro.exception.DiaryApplicationException;
import com.technophiles.Diaryapppro.models.Diary;
import com.technophiles.Diaryapppro.models.User;

import javax.validation.constraints.NotNull;

public interface UserService {

    UserDto createAccount(String email, String password) throws DiaryApplicationException;
    Diary addDiary(@NotNull Long id, @NotNull Diary diary) throws DiaryApplicationException;
    User findById(Long userId) throws DiaryApplicationException;
    boolean deleteUser(User user);
}
