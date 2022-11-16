package com.example.SWExhibition.controller;

import com.example.SWExhibition.dto.RatingsDto;
import com.example.SWExhibition.entity.Ratings;
import com.example.SWExhibition.service.RatingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RatingsController {

    private final RatingsService ratingsService;

    // 평점 정보 받아오기
    @PostMapping("/movie/rating")
    public String getRating(RatingsDto dto) {
        Ratings rating = ratingsService.save(dto);    // 평점 정보 저장

        return "redirect:/movie/" + rating.getMovie().getMovieCd();
    }
}
