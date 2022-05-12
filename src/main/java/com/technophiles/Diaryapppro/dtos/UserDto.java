package com.technophiles.Diaryapppro.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technophiles.Diaryapppro.models.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties("diaries")
public class UserDto {

    private Long id;
    private String email;
    private Set<Diary> diaries;
}

