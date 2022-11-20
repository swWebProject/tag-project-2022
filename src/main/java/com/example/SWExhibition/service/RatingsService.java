package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.RatingsDto;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Ratings;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.repository.RatingsRepository;
import com.example.SWExhibition.repository.UsersRepository;
import com.example.SWExhibition.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class RatingsService {

    private final RatingsRepository ratingsRepository;
    private final MoviesRepository moviesRepository;
    private final UsersRepository usersRepository;

    // 영화의 평균 평점
    @Transactional(readOnly = true)
    public Float avgRating(Movies movies) {
        return ratingsRepository.avg(movies);
    }

    // 해당 영화의 평가 수
    @Transactional(readOnly = true)
    public Long countRating(Movies movies) {
        return ratingsRepository.countByMovie(movies);
    }

    // 유저가 평가한 영화 리스트
    @Transactional(readOnly = true)
    public List<Ratings> ratedMovie(PrincipalDetails principalDetails) {
        Users user = usersRepository.findByUserId(principalDetails.getUsername()).orElse(null); // 유저에 대한 정보
        List<Ratings> ratingsList = ratingsRepository.findByUser(user);
        log.info(ratingsList.toString());
        
        return ratingsList;
    }
    
    // 유저와 영화 값에 해당하는 평점 정보 
    @Transactional(readOnly = true)
    public Ratings getRating(PrincipalDetails principalDetails, String movieCd) {
        Users user = usersRepository.findByUserId(principalDetails.getUsername()).orElse(null); // 유저에 대한 정보
        Movies movie = moviesRepository.findByMovieCd(movieCd); // 해당 영화에 대한 정보
        
        return ratingsRepository.findByUserAndMovie(user, movie);
    }

    // 저장
    @Transactional
    public Ratings save(RatingsDto dto, PrincipalDetails principalDetails) {
        dto.setUserId(principalDetails.getUsername());
        Users user = usersRepository.findByUserId(dto.getUserId()).orElse(null); // 유저에 대한 정보
        Movies movie = moviesRepository.findByMovieCd(dto.getMovieId()); // 해당 영화에 대한 정보
        Ratings rating = toEntity(dto);
        rating.setRating(rating.getRating());

        Ratings target = ratingsRepository.findByUserAndMovie(user, movie);

        // 업데이트
        if (target != null && !target.getRating().equals(dto.getRating())) {
            target.setRating(rating.getRating());
            log.info(target.toString());

            ratingsRepository.save(target); // 업데이트
        }
        // 새로운 정보면
        else if (target == null) 
            ratingsRepository.save(rating); // 그냥 저장
        
        // 별점 값이 원래 값이랑 같으면 DB에서 삭제
        else if (dto.getRating().equals(target.getRating())) 
            ratingsRepository.deleteById(target.getId());

        updateAvgRating(dto.getMovieId());  // 평균 별점 업데이트

        return rating;
    }

    // 영화의 평균 별점 업데이트
    @Transactional
    public void updateAvgRating(String movieCd) {
        Movies updatedMovie = moviesRepository.findByMovieCd(movieCd);   // 평균 별점을 업데이트 시킬 영화

        Float averageRating = Math.round(ratingsRepository.avg(updatedMovie) * 10) / 10.0f;  // 평가한 영화의 평균 별점 (소수점 2자리에서 반올림)
        updatedMovie.setAverageRating(averageRating);   // 새로운 평균 별정 넣기

        moviesRepository.save(updatedMovie);    // 업데이트 시킴
        log.info(updatedMovie.toString());
    }

    // Dto -> Entity
    private Ratings toEntity(RatingsDto ratingsDto) {
        return Ratings.builder()
                .user(usersRepository.findByUserId(ratingsDto.getUserId()).orElse(null))
                .movie(moviesRepository.findByMovieCd(ratingsDto.getMovieId()))
                .rating(ratingsDto.getRating())
                .build();
    }
}
