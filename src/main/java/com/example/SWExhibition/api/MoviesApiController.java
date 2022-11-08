package com.example.SWExhibition.api;

import com.example.SWExhibition.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MoviesApiController {

    private final MoviesService moviesService;

    @GetMapping("/test")
    public String test() throws ParseException {
        return moviesService.movieInfo("기생충").toString();
    }
}
