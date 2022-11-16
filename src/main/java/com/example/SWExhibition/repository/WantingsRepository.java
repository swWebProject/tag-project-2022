package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.entity.Wantings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantingsRepository extends JpaRepository<Wantings, Long> {
    boolean existsByMovieAndUser(Movies movies, Users users);   // 영화, 유저 정보로 데이터가 존재하는지 확인
    Wantings deleteByMovieAndUser(Movies movies, Users users);  // 영화, 유저 정보로 해당 데이터 삭제
}
