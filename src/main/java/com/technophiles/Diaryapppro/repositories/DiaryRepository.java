package com.technophiles.Diaryapppro.repositories;

import com.technophiles.Diaryapppro.models.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
