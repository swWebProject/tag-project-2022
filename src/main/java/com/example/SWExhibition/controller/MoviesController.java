package com.example.SWExhibition.controller;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.repository.RatingsRepository;
import com.example.SWExhibition.repository.UsersRepository;
import com.example.SWExhibition.service.CommentsService;
import com.example.SWExhibition.service.MoviesService;
import com.example.SWExhibition.service.Movies_has_genresService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;
    private final Movies_has_genresService movies_has_genresService;
    private final RatingsRepository ratingsRepository;
    private final UsersRepository usersRepository;
    private final CommentsService commentsService;

    // 영화 상세 페이지
    @GetMapping("/movie/{movieCd}")
    public String show(@PathVariable String movieCd, Model model) {
        Movies movie = moviesService.show(movieCd); // DB에서 데이터를 가져옴
        String genres = movies_has_genresService.getGenres(movie);   // 해당 영화와 매핑된 장르 정보들 불러오기
        String openYear = "미개봉";    // 개봉년도 디폴트 값

        // 연도만 표시
        if (movie.getOpenDt() != null && !movie.getOpenDt().equals(""))
            openYear = movie.getOpenDt().substring(0, 4);

        // View에 데이터 값 전달
        model.addAttribute("movieInfo", movie);
        model.addAttribute("genres", genres);
        model.addAttribute("openYear", openYear);


        return "/movie/movie";
    }
}
