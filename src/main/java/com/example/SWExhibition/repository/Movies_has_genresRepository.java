package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Genres;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Movies_has_genres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Movies_has_genresRepository extends JpaRepository<Movies_has_genres, Long> {
    boolean existsByMovieIdAndGenreID(Movies movies, Genres genres);    // DB에서 영화, 장르 키값으로 조회
    List<Movies_has_genres> findByMovieId(Movies movies); // 영화 값으로 매핑된 정보 불러오기
}
