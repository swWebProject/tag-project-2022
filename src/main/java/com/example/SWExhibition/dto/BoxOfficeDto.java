package com.example.SWExhibition.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BoxOfficeDto {

    private Long id;
    private String movieCd; // 영화 코드
    private Integer rank;    // 순위
    private String audiCnt; // 해당일 관객수
    private String audiAcc; // 누적관객수

    @Builder
    public BoxOfficeDto(Long id, String movieCd, Integer rank, String audiCnt, String audiAcc) {
        this.id = id;
        this.movieCd = movieCd;
        this.rank = rank;
        this.audiCnt = audiCnt;
        this.audiAcc = audiAcc;
    }
}
