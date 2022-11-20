package com.example.SWExhibition.api;

import com.example.SWExhibition.dto.RatingsDto;
import com.example.SWExhibition.entity.Ratings;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.RatingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RatingsApiController {

    private final RatingsService ratingsService;

    // 평점 정보 받아오기
    @PostMapping("/api/post/movie/rating")
    public Ratings postRating(@RequestBody RatingsDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인 상태가 아니면 에러 발생
        if ( principalDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 필요!");
        }

        return ratingsService.save(dto, principalDetails);
    }

    // 영화 상세 정보 페이지에 평점 정보를 전달할 api
    @GetMapping("/api/get/movie/rating/movieCd={movieCd}")
    public Ratings getRating(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String movieCd) {
        // 로그인 상태가 아니면 에러 발생
        if ( principalDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 필요!");
        }

        return ratingsService.getRating(principalDetails, movieCd);
    }

    // 유저가 평가한 영화 목록
    @GetMapping("/api/get/rating")
    public List<Ratings> getWantingList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ratingsService.ratedMovie(principalDetails);
    }
}
