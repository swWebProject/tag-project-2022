package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.BoxOffice;
import com.example.SWExhibition.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxOfficeRepository extends JpaRepository<BoxOffice, Long> {
    boolean existsByMovies(Movies movies); // DB에서 해당하는 movie 찾기
    @Override
    List<BoxOffice> findAll();  // 박스오피스 리스트를 불러옴
}
