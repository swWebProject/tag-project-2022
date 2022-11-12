package com.example.SWExhibition.api;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.service.MoviesService;
import com.example.SWExhibition.service.ParticipantsService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MoviesApiController {

    private final MoviesService moviesService;
    private final ParticipantsService participantsService;

    // 주어진 이름과 관련있는 영화들 찾기
    @GetMapping("/api/movie/movieNm={movieNm}")
    public List<Movies> searchMovie(@PathVariable String movieNm) throws ParseException {
        return moviesService.search(movieNm);
    }
}
