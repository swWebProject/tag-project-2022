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
@RequestMapping("api/movie/")
public class CommentApiController {

    private final CommentsService commentsService;

    //댓글 저장
    @PostMapping("save/{movieCd}")
    public ResponseEntity commentSave(@PathVariable String movieCd, @RequestBody CommentRequestDto dto,
                                      @AuthenticationPrincipal PrincipalDetails user) {
        return ResponseEntity.ok(commentsService.commentSave(user.getNickname(), movieCd, dto));
    }

    /* DELETE */
    @DeleteMapping("delete/{movieCd}")
    public ResponseEntity deleteComment(@PathVariable String movieCd) {
        commentsService.deleteComment(movieCd);
        return ResponseEntity.ok(movieCd);
    }
}
