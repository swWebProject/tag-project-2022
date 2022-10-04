package com.example.SWExhibition.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@DynamicUpdate  // 변경된 값만 업데이트
public class Movies {
    @Id
    private String movieCd; // 영화 코드

    @Column(nullable = false)
    private String movieNm; // 영화 이름

    @Column
    private byte[] poster;  // 포스터 사진 파일

    @Column(nullable = false)
    private String openDtr; // 개봉일

    @Column
    private String nationAlt;   // 제작 국가

    @Column(name = "average_rating")
    private Float averageRating;    // 평균 평점

    @ManyToOne
    @JoinColumn(name = "participant_ID")
    private Participants participant;    // 감독이나 배우 코드

    @ManyToOne
    @JoinColumn(name = "genre_ID")
    private Genres genre;    // 장르 키 값


}
