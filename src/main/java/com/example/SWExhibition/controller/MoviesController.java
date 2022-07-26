package com.example.SWExhibition.controller;

import com.example.SWExhibition.dto.CommentResponseDto;
import com.example.SWExhibition.entity.Comments;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.CommentsService;
import com.example.SWExhibition.service.MoviesService;
import com.example.SWExhibition.service.Movies_has_genresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MoviesController {

    private final MoviesService moviesService;
    private final Movies_has_genresService movies_has_genresService;
    private final CommentsService commentsService;

    // 영화 상세 페이지
    @GetMapping("movie/{movieCd}")
    public String show(@PathVariable String movieCd, Model model, @AuthenticationPrincipal PrincipalDetails user) {
        Movies movie = moviesService.show(movieCd); // DB에서 데이터를 가져옴
        String genres = movies_has_genresService.getGenres(movie);   // 해당 영화와 매핑된 장르 정보들 불러오기
        String openYear = "미개봉";    // 개봉년도 디폴트 값

        // 평균 별점 디폴트 값 설정
        if (movie.getAverageRating() == null)
            movie.setAverageRating(0.0f);

        // 연도만 표시
        if (movie.getOpenDt() != null && !movie.getOpenDt().equals(""))
            openYear = movie.getOpenDt().substring(0, 4);

        // View에 데이터 값 전달
        model.addAttribute("movieInfo", movie);
        model.addAttribute("genres", genres);
        model.addAttribute("openYear", openYear);

        //댓글 기능
        List<Comments> comments = commentsService.show(movie);
        List<CommentResponseDto> dto = new ArrayList<>();

        /* 사용자 관련 */
        if (user != null && comments != null) {

            /* 댓글 작성자 본인인지 확인 */
            for (Comments comment : comments) {
                //댓글 작성자 id와 현재 사용자 id를 비교해 true/false 판단
                boolean isWriter = comment.getUser().getNickname().equals(user.getNickname());
                dto.add(new CommentResponseDto(comment.getCommentID(), comment.getComment(), comment.getUser(), comment.getMovieCd(), isWriter));
            }

        }

        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", dto);
        }

        model.addAttribute("movie", movie);

        //댓글 좋아요


        return "/movie/movie";
    }

}
