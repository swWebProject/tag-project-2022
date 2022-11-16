package com.example.SWExhibition.api;

import com.example.SWExhibition.entity.Ratings;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.RatingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RatingsApiController {

    private final RatingsService ratingsService;

    // 영화 상세 정보 페이지에 평점 정보를 전달할 api
    @GetMapping("/api/get/rating/movieCd={movieCd}")
    public Ratings getRating(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String movieCd) {
        return ratingsService.getRating(principalDetails, movieCd);
    }
}
