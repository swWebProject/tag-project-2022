package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenresRepository extends JpaRepository<Genres, Long> {
    boolean existsByGenreAlt(String genreAlt);  // DB에서 장르 여부 확인
    Genres findByGenreAlt(String genreAlt); // DB에서 장르명 꺼내오기
}
