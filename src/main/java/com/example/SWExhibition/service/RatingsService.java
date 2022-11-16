package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.RatingsDto;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Ratings;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.repository.RatingsRepository;
import com.example.SWExhibition.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class RatingsService {

    private final RatingsRepository ratingsRepository;
    private final MoviesRepository moviesRepository;
    private final UsersRepository usersRepository;

    // 영화의 평균 평점
    public Float avgRating(Movies movies) {
        return ratingsRepository.avg(movies);
    }

    // 해당 영화의 평가 수
    public Long countRating(Movies movies) {
        return ratingsRepository.countByMovie(movies);
    }

    // 유저가 평가한 영화 리스트
    public List<Movies> ratedMovie(Users users) {
        List<Ratings> ratingsList = ratingsRepository.findByUser(users);
        log.info(ratingsList.toString());
        List<Movies> movieList = new ArrayList<>();

        // 평가한 영화 리스트
        for (Ratings rating : ratingsList) {
            movieList.add(rating.getMovie());
        }
        
        return movieList;
    }

    // 저장
    @Transactional
    public Ratings save(RatingsDto dto) {
        Ratings rating = toEntity(dto);

        Ratings target = ratingsRepository.findByUserAndMovie(rating.getUser(), rating.getMovie());

        // 업데이트
        if (target != null && target.getRating() != rating.getRating()) {
            rating.setId(target.getId());
            log.info(rating.toString());

            ratingsRepository.save(rating); // 업데이트
        }
        // 새로운 정보면
        else if (target == null)
            ratingsRepository.save(rating); // 그냥 저장

        return rating;
    }

    // 현재 날짜와 시간을 알아옴
    public String ratedDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.toString();
    }

    // Dto -> Entity
    private Ratings toEntity(RatingsDto ratingsDto) {
        return Ratings.builder()
                .user(usersRepository.findByUserId(ratingsDto.getUserId()).orElse(null))
                .movie(moviesRepository.findByMovieCd(ratingsDto.getMovieId()))
                .rating(ratingsDto.getRating())
                .date(ratedDateTime())
                .build();
    }
}
