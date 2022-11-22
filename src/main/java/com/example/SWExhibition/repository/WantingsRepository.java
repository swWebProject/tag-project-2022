package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.entity.Wantings;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WantingsRepository extends JpaRepository<Wantings, Long> {
    boolean existsByMovieAndUser(Movies movies, Users users);   // 영화, 유저 정보로 데이터가 존재하는지 확인
    void deleteByMovieAndUser(Movies movies, Users users);  // 영화, 유저 정보로 해당 데이터 삭제
    void deleteByUser(Users users); // 유저 정보에 해당하는 목록 삭제
    List<Wantings> findByUser(Users users, Sort sort); // 해당 유저가 등록한 보고 싶은 영화 목록 (최근 등록한 순)
}
