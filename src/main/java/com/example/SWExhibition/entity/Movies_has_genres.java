package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Movies_has_genres {
    @Id
    @ManyToOne
    @JoinColumn(name = "movies_moiveCd")
    private Movies movieCd;

    @ManyToOne
    @JoinColumn(name = "genres_genreID")
    private Genres genreID;
}
