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
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer genreID;   // 장르 ID

    @Column(nullable = false, unique = true)
    private String genreAlt;    // 장르명
}