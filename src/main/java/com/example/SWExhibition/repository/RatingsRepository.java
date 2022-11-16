package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Ratings;
import com.example.SWExhibition.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    @Query (value = "select avg(rating)" +
            "from Ratings " +
            "where movie = :movie")
    Float avg(@Param("movie") Movies movie);    // 해당 영화에 대한 평균 평점
    Long countByMovie(Movies movie);    // 해당 영화에 대해 평가한 사람 수
    List<Ratings> findByUser(Users user);  // 유저 값으로 평가한 목록들 불러오기
    Ratings findByUserAndMovie(Users users, Movies movies);  // 영화, 유저 값으로 데이터 불러오기
    boolean existsByMovieAndUser(Movies movie, Users user); // 영화와 유저 값으로 데이터 확인
}
