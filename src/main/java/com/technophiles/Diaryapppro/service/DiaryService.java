package com.technophiles.Diaryapppro.service;

import com.technophiles.Diaryapppro.models.Diary;
import com.technophiles.Diaryapppro.models.User;

public interface DiaryService {

    Diary createDiary(String title, User user);
}
