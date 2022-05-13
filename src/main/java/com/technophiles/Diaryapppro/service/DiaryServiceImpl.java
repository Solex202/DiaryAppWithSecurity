package com.technophiles.Diaryapppro.service;

import com.technophiles.Diaryapppro.models.Diary;
import com.technophiles.Diaryapppro.models.User;
import com.technophiles.Diaryapppro.repositories.DiaryRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    @Autowired
    DiaryRepository diaryRepository;
    @Override
    public Diary createDiary(String title, User user) {
        Diary diary = new Diary(title);
        diary.setUser(user);
        return diaryRepository.save(diary);
    }


}
