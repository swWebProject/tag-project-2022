package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
    boolean existsByPeopleCd(String peopleCd);  // DB에서 peopleCd로 조회
    Participants findByPeopleCd(String peopleCd);   // DB에서 peopleCd로 가져옴
}
