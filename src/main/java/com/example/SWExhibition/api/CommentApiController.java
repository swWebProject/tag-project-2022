package com.example.SWExhibition.api;


import com.example.SWExhibition.dto.CommentRequestDto;
import com.example.SWExhibition.entity.Comments;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/movie/")
public class CommentApiController {

    private final CommentsService commentsService;
    private final MoviesRepository moviesRepository;

    //댓글 저장
    @PostMapping("save/{movieCd}")
    public ResponseEntity<?> commentSave(@PathVariable String movieCd, @RequestBody CommentRequestDto dto,
                                                @AuthenticationPrincipal PrincipalDetails user) {
        return ResponseEntity.ok(commentsService.commentSave(user.getNickname(), movieCd, dto));
    }

    // 댓글 목록
    @GetMapping("show/{movieCd}")
    public List<Comments> list(@PathVariable String movieCd, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Movies movie = moviesRepository.findByMovieCd(movieCd);
        if (principalDetails != null)
            return null;

        return commentsService.show(movie);
    }

    /* DELETE */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentsService.deleteComment(id);
        return ResponseEntity.ok(id);
    }
}
