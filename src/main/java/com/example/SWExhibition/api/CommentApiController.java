package com.example.SWExhibition.api;


import com.example.SWExhibition.dto.CommentDto;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentsService commentsService;

    @PostMapping("/api/post/{movieCd}/comment")
    public ResponseEntity commentSave(@PathVariable String nickname, @RequestBody CommentDto.CommentsRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(commentsService.commentsSave(principalDetails.getNickname(), nickname, dto));
    }
}
