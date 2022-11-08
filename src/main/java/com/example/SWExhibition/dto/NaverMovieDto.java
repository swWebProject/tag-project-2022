package com.example.SWExhibition.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class NaverMovieDto {

    private String title;   // 영화 제목
    private String image;   // 이미지 url
    private String pubDate;   // 개봉연도
    private String director;    // 감독명

    @Builder
    public NaverMovieDto(String title, String image, String pubDate, String director) {
        this.title = title;
        this.image = image;
        this.pubDate = pubDate;
        this.director = director;
    }
}
