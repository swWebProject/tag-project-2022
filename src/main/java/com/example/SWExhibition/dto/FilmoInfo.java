package com.example.SWExhibition.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FilmoInfo {

    private String movieCd; // 영화 코드
    private String moviePartNm; // 참여 분야

    @Builder
    public FilmoInfo(String movieCd, String moviePartNm) {
        this.movieCd = movieCd;
        this.moviePartNm = moviePartNm;
    }
}
