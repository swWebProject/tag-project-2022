package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Genres;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Movies_has_genres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Movies_has_genresRepository extends JpaRepository<Movies_has_genres, Long> {
    boolean existsByMovieIdAndGenreID(Movies movies, Genres genres);
}
