package com.example.SWExhibition.api;


import com.example.SWExhibition.dto.CommentRequestDto;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/movie")
public class CommentApiController {

    private final CommentsService commentsService;

    //댓글 저장
    @PostMapping("{movieCd}")
    public ResponseEntity commentSave(@PathVariable String movieCd, @RequestBody CommentRequestDto dto,
                                      @AuthenticationPrincipal PrincipalDetails user) {
        return ResponseEntity.ok(commentsService.commentSave(user.getNickname(), Long.valueOf(movieCd), dto));
    }

    /* UPDATE */
    @PutMapping({"{movieCd}"})
    public ResponseEntity update(@PathVariable String movieCd, @RequestBody CommentRequestDto dto) {
        commentsService.update(Long.valueOf(movieCd), dto);
        return ResponseEntity.ok(movieCd);
    }

    /* DELETE */
    @DeleteMapping("{movieCd}")
    public ResponseEntity delete(@PathVariable String movieCd) {
        commentsService.delete(Long.valueOf(movieCd));
        return ResponseEntity.ok(movieCd);
    }
}
