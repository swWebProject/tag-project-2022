package com.example.SWExhibition.api;

import com.example.SWExhibition.dto.PosterUrlDto;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MoviesApiController {

    private final MoviesService moviesService;

    // 주어진 이름과 관련있는 영화들 찾기
    @GetMapping("/api/movie/movieNm={movieNm}")
    public List<Movies> searchMovie(@PathVariable String movieNm) throws ParseException {
        return moviesService.search(movieNm);
    }

    // 영화인의 이름으로 검색하여 필모그래피로 영화 데이터를 DB에 저장
    @GetMapping("/api/movie/peopleNm={peopleNm}")
    public void saveMovie(@PathVariable String peopleNm) throws ParseException {
        moviesService.saveAsPeople(peopleNm);
    }

    // 영화인의 이름과 참여한 영화로 검색하여 필모그래피로 영화 데이터를 DB에 저장
    @GetMapping("/api/movie/peopleNm={peopleNm}&movieNm={movieNm}")
    public void saveMovie1(@RequestParam(value = "peopleNm") String peopleNm, @RequestParam(value = "movieNm") String movieNm) throws ParseException {
        moviesService.saveAsPeople(peopleNm, movieNm);
    }

    // 영화 poster 수정
    @PutMapping("/api/movie/poster")
    public Movies updatePoster(@RequestBody PosterUrlDto dto) {
        return moviesService.updatePoster(dto);
    }
}
