package com.example.SWExhibition.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
public class BoxOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    // 객체 id

    @OneToOne
    @JoinColumn(name = "movies_movieCd")
    private Movies movies; // 영화 객체

    @Column(nullable = false)
    private String rank;    // 순위

    @Column
    private String audiCnt; // 해당일 관객수

    @Column
    private String audiAcc; // 누적관객수

    @Builder
    public BoxOffice(Long id, Movies movies, String rank, String audiCnt, String audiAcc) {
        this.id = id;
        this.movies = movies;
        this.rank = rank;
        this.audiCnt = audiCnt;
        this.audiAcc = audiAcc;
    }
}
