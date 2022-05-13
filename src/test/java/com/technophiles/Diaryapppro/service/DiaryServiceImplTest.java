package com.technophiles.Diaryapppro.service;

import com.technophiles.Diaryapppro.dtos.UserDto;
import com.technophiles.Diaryapppro.models.Diary;
import com.technophiles.Diaryapppro.models.User;
import com.technophiles.Diaryapppro.repositories.DiaryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class DiaryServiceImplTest {
    @Autowired
    private DiaryRepository diaryRepository;
    @Test
    void createDiary() {
    }

    @Test
    void findDiaryById() {
        //given
        User user =  User.builder().password("lotachi123").email("lota@email.com").build();
        Diary diary = new Diary();
        //when
//        diaryRepository.findDiaryById( user , Long diaryId)

    }
}