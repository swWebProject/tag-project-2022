package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Box-Office")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class BoxOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "movies_movieCd")
    private String movieCd; // 영화 코드

    @Column(nullable = false)
    private String rank;    // 순위

    @Column
    private String audiAcc; // 관객수
}
