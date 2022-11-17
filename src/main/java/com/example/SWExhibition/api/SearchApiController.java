package com.example.SWExhibition.api;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchApiController {
    @Autowired
    private final MoviesService moviesService;

    @GetMapping("/search/{keyword}")
    public List<Movies> keywordMovieSearchForm(@RequestParam("keyword") @PathVariable String keyword) throws ParseException {

        return moviesService.searchMovies(keyword);
    }


}
