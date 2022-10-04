package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Locations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationID;    // 촬영지 ID

    @ManyToOne
    @JoinColumn(name = "movies_moiveCd", nullable = false)
    private Movies movie; // 영화 코드

    @Column(nullable = false)
    private String locationNm;  // 촬영지 이름

    @Column(name = "do", nullable = false)
    private String _do;  // 도

    @Column(nullable = false)
    private String si_gun_gu;   // 시군구

    @Column(nullable = false)
    private String dong_eup_myeon;  // 동읍면

    @Column
    private String detailed;    // 나머지 주소

}
