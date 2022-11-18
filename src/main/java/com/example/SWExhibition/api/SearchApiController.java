package com.example.SWExhibition.api;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchApiController {

    private final MoviesService moviesService;

    @GetMapping("/search/{keyword}")
    public String keywordMovieSearchForm(@PathVariable String keyword, Model model) {
        // 검색 결과 영화 가져오기
        List<Movies> movieList = moviesService.searchMovies(keyword);
        List<Movies> searchMovieList = new ArrayList<>();

        if (movieList != null) {
            for (Movies movie : movieList) {
                // 연도만 표시
                if (movie.getOpenDt() != null && !movie.getOpenDt().equals(""))
                    movie.setOpenDt(movie.getOpenDt().substring(0, 4));
                else
                    movie.setOpenDt("미개봉");

                searchMovieList.add(movie);
            }
        }

        model.addAttribute("searchMovieList", searchMovieList);

        return "/search/search";
    }


}
