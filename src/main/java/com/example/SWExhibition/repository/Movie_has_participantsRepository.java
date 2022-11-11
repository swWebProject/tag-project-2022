package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Movies_has_participants;
import com.example.SWExhibition.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Movie_has_participantsRepository extends JpaRepository<Movies_has_participants, Long> {
    boolean existsByMoiveIdAndPeopleIdAndMoviePartNm(Movies movies, Participants participants, String moviePartNm);    // DB에 특정 행이 있는지 확인
}
