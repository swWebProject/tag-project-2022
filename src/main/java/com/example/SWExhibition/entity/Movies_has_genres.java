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
public class Movies_has_genres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 영화&장르 ID

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movies_id")
    private Movies movieId; // 영화 코드

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genres_genreID")
    private Genres genreID; // 장르 ID

    @Builder
    public Movies_has_genres(Long id, Movies movieCd, Genres genreID) {
        this.id = id;
        this.movieId = movieCd;
        this.genreID = genreID;
    }
}
