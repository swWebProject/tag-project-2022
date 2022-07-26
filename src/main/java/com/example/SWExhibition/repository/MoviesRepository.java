package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {
    boolean existsByMovieCd(String movieCd);    // DB에 해당 영화 코드가 있는지 검사
    Movies findByMovieCd(String movieCd); // DB에서 MovieCd로 데이터 불러오기
    List<Movies> findByMovieNmContains(String keyword); // 영화 이름에서 keyword가 포함된 영화들 불러오기
}
