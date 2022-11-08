package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Movies_has_genres {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long moviesGenresId;  // 영화&장르 ID

    @ManyToOne
    @JoinColumn(name = "movies_moiveCd")
    private Movies movieCd; // 영화 코드

    @ManyToOne
    @JoinColumn(name = "genres_genreID")
    private Genres genreID; // 장르 ID
}
