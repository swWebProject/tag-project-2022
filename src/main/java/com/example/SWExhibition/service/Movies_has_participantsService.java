package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.FilmoInfo;
import com.example.SWExhibition.dto.ParticipantsDto;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Movies_has_participants;
import com.example.SWExhibition.entity.Participants;
import com.example.SWExhibition.repository.Movie_has_participantsRepository;
import com.example.SWExhibition.repository.ParticipantsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class Movies_has_participantsService {

    private final Movie_has_participantsRepository movie_has_participantsRepository;
    private final ParticipantsRepository participantsRepository;
    private final MoviesService moviesService;

    // Movies & Participants 매핑한 값 저장
    @Transactional
    public void save(ParticipantsDto dto) throws ParseException {
        Participants participant = participantsRepository.findByPeopleCd(dto.getPeopleCd());

        List<FilmoInfo> filmoInfoList = dto.getFilmoInfoList(); // 필모그래피에 대한 정보 리스트
        for (FilmoInfo info : filmoInfoList) {
            String moviePartNm = info.getMoviePartNm(); // 참여 분야
            Movies movies = moviesService.findWithMovieCode(info.getMoiveCd());   // 해당 영화 코드에 맞는 정보 가져오기

            if (!movie_has_participantsRepository.existsByMoiveIdAndPeopleIdAndMoviePartNm(movies, participant, moviePartNm)) {
                Movies_has_participants entity = toEntity(movies, participant, moviePartNm);  // 영화 & 영화인 entity
                movie_has_participantsRepository.save(entity);
                log.info(entity.toString());
            }
        }
    }

    // Movies + Participants -> Mvoie_has_participants Entity
    private Movies_has_participants toEntity(Movies movies, Participants participants, String moviePartNm) {
        return Movies_has_participants.builder()
                .peopleId(participants)
                .moviePartNm(moviePartNm)
                .moiveId(movies)
                .build();
    }
}
