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
    public ResponseEntity update(@PathVariable Long id, @RequestBody CommentRequestDto dto) {
        commentsService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("{movieCd}")
    public ResponseEntity delete(@PathVariable Long id) {
        commentsService.delete(id);
        return ResponseEntity.ok(id);
    }
}
