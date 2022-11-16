package com.example.SWExhibition.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 객체 id

    @Column(nullable = false, unique = true)
    private String movieCd; // 영화 코드

    @Column(nullable = false)
    private String movieNm; // 영화 이름

    @Column
    private String movieNmEn; // 영화 영어 제목

    @Column
    private String poster;  // 포스터 사진 url

    @Column
    private String openDt; // 개봉일

    @Column
    private String nationAlt;   // 제작 국가

    @Column
    private Float averageRating;    // 평균 평점

    @ManyToOne
    private Comments comments;
    @Builder
    public Movies(Long id, String movieCd, String movieNm, String movieNmEn, String poster, String openDt, String nationAlt, Float averageRating) {
        this.id = id;
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.movieNmEn = movieNmEn;
        this.poster = poster;
        this.openDt = openDt;
        this.nationAlt = nationAlt;
        this.averageRating = averageRating;
    }
}
