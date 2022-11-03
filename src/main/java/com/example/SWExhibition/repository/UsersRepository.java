package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserId(String userId);    // userId 값으로 불러옴
    boolean existsByUserId(String userId);  // DB에 userId 존재 유무
    boolean existsByNickname(String nickname);  // DB에 nickname 존재 유무
}
