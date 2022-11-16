package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.WatchingsDto;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.entity.Watchings;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.repository.UsersRepository;
import com.example.SWExhibition.repository.WatchingsRepository;
import com.example.SWExhibition.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class WatchingsService {

    private final WatchingsRepository watchingsRepository;
    private final MoviesRepository moviesRepository;
    private final UsersRepository usersRepository;

    public Watchings updateWatching(PrincipalDetails principalDetails, WatchingsDto dto) {
        Watchings entity = toEntity(principalDetails, dto);
        log.info(entity.toString());

        // key 값이 0이 아니면 저장
        if (dto.getKeyValue() != 0) {
            watchingsRepository.save(entity);
        }
        // 0이면 삭제
        else
            watchingsRepository.deleteByMovieAndUser(entity.getMovie(), entity.getUser());

        return entity;
    }

    // 유저가 보는 중인 영화 목록
    public List<Watchings> showWatchings(PrincipalDetails principalDetails) {
        Users user = usersRepository.findByUserId(principalDetails.getUsername()).orElse(null); // 유저 정보 가져 오기

        List<Watchings> watchingsList = watchingsRepository.findByUser(user); // 유저가 보는 중인 영화 목록 반환
        log.info(watchingsList.toString());

        return watchingsList;
    }

    // 현재 날짜와 시간을 알아옴
    public String wantedDate() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.toString();
    }

    // Dto -> Entity
    private Watchings toEntity(PrincipalDetails principalDetails, WatchingsDto dto) {
        return Watchings.builder()
                .movie(moviesRepository.findByMovieCd(dto.getMovieId()))
                .user(usersRepository.findByUserId(principalDetails.getUsername()).orElse(null))
                .date(wantedDate())
                .build();
    }
}
