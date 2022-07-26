package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.WatchingsDto;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.entity.Watchings;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.repository.UsersRepository;
import com.example.SWExhibition.repository.WatchingsRepository;
import com.example.SWExhibition.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class WatchingsService {

    private final WatchingsRepository watchingsRepository;
    private final MoviesRepository moviesRepository;
    private final UsersRepository usersRepository;

    @Transactional
    // 보는 중인 영화 목록에 추가 또는 삭제
    public Integer updateWatching(PrincipalDetails principalDetails, WatchingsDto dto) {
        Watchings entity = toEntity(principalDetails, dto);
        log.info(entity.toString());

        // DB에 없으면 저장
        if (!watchingsRepository.existsByMovieAndUser(entity.getMovie(), entity.getUser())) {
            watchingsRepository.save(entity);
            return 1;
        }
        // 있으면 삭제
        else {
            watchingsRepository.deleteByMovieAndUser(entity.getMovie(), entity.getUser());
            return 0;
        }
    }

    // 유저가 보는 중인 영화 목록
    @Transactional(readOnly = true)
    public List<Watchings> showWatchings(PrincipalDetails principalDetails) {
        Users user = usersRepository.findByUserId(principalDetails.getUsername()).orElse(null); // 유저 정보 가져 오기
        Sort sort = sortByModifiedDate();   // modifiedDate를 기준으로 내림차순 정렬

        List<Watchings> watchingsList = watchingsRepository.findByUser(user, sort); // 유저가 보는 중인 영화 목록 반환
        log.info(watchingsList.toString());

        return watchingsList;
    }

    // 영화와 사용자 정에 해당하는 값 불러오기
    @Transactional(readOnly = true)
    public Boolean show(PrincipalDetails principalDetails, String movieCd) {
        Users user = usersRepository.findByUserId(principalDetails.getUsername()).orElse(null); // 유저 정보 가져오기
        Movies movie = moviesRepository.findByMovieCd(movieCd); // 요청 영화 정보 가져오기

        return watchingsRepository.existsByMovieAndUser(movie, user);    // 존재 여부 확인
    }

    // Dto -> Entity
    private Watchings toEntity(PrincipalDetails principalDetails, WatchingsDto dto) {
        return Watchings.builder()
                .movie(moviesRepository.findByMovieCd(dto.getMovieId()))
                .user(usersRepository.findByUserId(principalDetails.getUsername()).orElse(null))
                .build();
    }

    // modifiedDate를 기준으로 내림차순 정렬
    private Sort sortByModifiedDate() {
        return Sort.by(Sort.Direction.DESC, "modifiedDate");
    }
}
