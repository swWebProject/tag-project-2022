package com.example.SWExhibition.dto;

import lombok.Data;

@Data
public class RatingsDto {

    private String userId;  // 유저 ID
    private String movieId; // 영화 ID
    private Float rating;    // 평점
}
