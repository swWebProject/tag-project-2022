package com.example.SWExhibition.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreID;   // 장르 ID

    @Column(nullable = false, unique = true)
    private String genreAlt;    // 장르명

    @Builder
    public Genres(Long genreID, String genreAlt) {
        this.genreID = genreID;
        this.genreAlt = genreAlt;
    }
}