package com.example.SWExhibition.controller;

import com.example.SWExhibition.dto.MovieDto;
import com.example.SWExhibition.dto.NaverMovieDto;
import com.example.SWExhibition.service.MoviesService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class SearchController {

    private final MoviesService moviesService;

    public SearchController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping("/home/search/{keyword}")
    public String searchMovie(@RequestParam(value = "keyword") String keyword, Model model) {
        List<MovieDto> movieDtoList = moviesService.searchMovies(keyword);
        List<NaverMovieDto> movieImage = moviesService.searchMoviesImage(keyword);
        model.addAttribute("movieDtoList",movieDtoList);
        model.addAttribute("movieImage", movieImage);

        return "home/search.html";
    }

}
