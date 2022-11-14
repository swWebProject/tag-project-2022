package com.example.SWExhibition.controller;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.service.MoviesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/home/search")
public class SearchController {

    private final MoviesService moviesService;

    public SearchController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping("/{keyword}")
    public String searchMovie(@RequestParam String keyword, Model model) {
        List<Movies> movieList = moviesService.searchMovies(keyword);
        model.addAttribute("movieList",movieList);


        return "home/search";
    }

}
