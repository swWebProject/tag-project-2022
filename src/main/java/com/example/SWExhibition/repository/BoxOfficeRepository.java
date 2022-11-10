package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.BoxOffice;
import com.example.SWExhibition.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxOfficeRepository extends JpaRepository<BoxOffice, Long> {
    boolean existsByMovies(Movies movies); // DB에서 해당하는 movie 찾기
}
