package com.example.SWExhibition.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class NaverMovieDto {

    private String title;   // 영화 제목,
    private String subtitle; // 영화 영어제목 또는 원제
    private String image;   // 이미지 url
    private String director;    // 감독명,  '|'으로 감독명 구분
    private String actors;   // 주요 배우명, '|'으로 배우명 구분

    @Builder
    public NaverMovieDto(String title, String subtitle, String image, String director, String actors) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.director = director;
        this.actors = actors;
    }
}
