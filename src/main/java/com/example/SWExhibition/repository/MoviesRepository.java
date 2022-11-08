package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {
    boolean existsByMovieCd(String movieCd);    // DB에 해당 영화 코드가 있는지 검사
}
