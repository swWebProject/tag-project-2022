package com.example.SWExhibition.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MovieDto {

    private String movieCd; // 영화 코드
    private String movieNm; // 영화 이름
    private String movieNmEn; // 영화 영어 제목
    private String openDt;  // 개봉일
    private String nationAlt;   // 제작 국가
    private String genreAlt;    // 장르, ','으로 구분
    private String directors;    // 감독명, '|'으로 감독명 구분

    @Builder
    public MovieDto(String movieCd, String movieNm, String movieNmEn, String openDt, String nationAlt, String genreAlt, String directors) {
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.movieNmEn = movieNmEn;
        this.nationAlt = nationAlt;
        this.genreAlt = genreAlt;

        // "" 값이 들어오면 저장 안함 -> null
        if (!openDt.equals(""))
            this.openDt = openDt;

        // "" 값이 들어오면 저장 안함 -> null
        if (!directors.equals(""))
            this.directors = directors;
    }
}
