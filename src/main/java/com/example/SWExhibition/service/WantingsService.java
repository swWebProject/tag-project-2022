package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.WantingsDto;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.entity.Wantings;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.repository.UsersRepository;
import com.example.SWExhibition.repository.WantingsRepository;
import com.example.SWExhibition.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class WantingsService {

    private final WantingsRepository wantingsRepository;
    private final MoviesRepository moviesRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Wantings updateWanting(PrincipalDetails principalDetails, WantingsDto dto) {
        Wantings entity = toEntity(principalDetails, dto);
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
    
    public List<Wantings> showWantings(PrincipalDetails principalDetails) {
        Users user = usersRepository.findByUserId(principalDetails.getUsername()).orElse(null); // 유저 정보 가져 오기
        
        return wantingsRepository.findByUser(user); // 유저가 보고 싶은 영화 목록 반환
    }

    // 현재 날짜와 시간을 알아옴
    public String wantedDate() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.toString();
    }

    // Dto -> Entity
    public Wantings toEntity(PrincipalDetails principalDetails, WantingsDto dto) {
        return Wantings.builder()
                .movie(moviesRepository.findByMovieCd(dto.getMovieId()))
                .user(usersRepository.findByUserId(principalDetails.getUsername()).orElse(null))
                .date(wantedDate())
                .build();
    }
}


