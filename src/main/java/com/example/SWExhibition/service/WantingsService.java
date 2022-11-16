package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.WantingsDto;
import com.example.SWExhibition.entity.Wantings;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.repository.UsersRepository;
import com.example.SWExhibition.repository.WantingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class WantingsService {

    private final WantingsRepository wantingsRepository;
    private final MoviesRepository moviesRepository;
    private final UsersRepository usersRepository;

    public Wantings updateWanting(WantingsDto dto) {
        Wantings entity = toEntity(dto);
        log.info(entity.toString());

        // key 값이 0이 아니면 저장
        if (dto.getKeyValue() != 0) {
            wantingsRepository.save(entity);
        }
        // 0이면 삭제
        else
            wantingsRepository.deleteByMovieAndUser(entity.getMovie(), entity.getUser());

        return entity;
    }

    // 현재 날짜와 시간을 알아옴
    public String wantedDate() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.toString();
    }

    // Dto -> Entity
    private Wantings toEntity(WantingsDto dto) {
        return Wantings.builder()
                .movie(moviesRepository.findByMovieCd(dto.getMovieId()))
                .user(usersRepository.findByUserId(dto.getUserId()).orElse(null))
                .date(wantedDate())
                .build();
    }
}


