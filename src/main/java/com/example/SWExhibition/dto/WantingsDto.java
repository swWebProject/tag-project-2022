package com.example.SWExhibition.dto;

import lombok.Data;

@Data
public class WantingsDto {

    private String userId;  // 유저 ID
    private String movieId; // 영화 ID
    private Long keyValue;    // 응답 정보값 ( 1이면 DB에 추가, 0이면 DB에서 삭제)
}
