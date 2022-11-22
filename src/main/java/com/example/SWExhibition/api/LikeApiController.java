package com.example.SWExhibition.api;

import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class LikeApiController {

    private final LikeService likeService;

    @GetMapping("/countLike/{commentId}")
    public ResponseEntity<List<String>> getLikeCount(@PathVariable Long commentId,
                                                     @AuthenticationPrincipal PrincipalDetails user) {
        log.info("commentId : {} ", commentId);
        log.info("user : {} ", user);

        List<String> resultData = likeService.count(commentId, user);

        log.info("likeCount : {} ", resultData);

        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @DeleteMapping("/cancelLike/{commentId}")
    public ResponseEntity<String> cancelLike(@PathVariable Long commentId,
                                             @AuthenticationPrincipal PrincipalDetails user) {
        if (user != null) {
            likeService.cancelLike(user, commentId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addLike/{commentId}")
    public ResponseEntity<String> addLike(@PathVariable Long commentId,
                                          @AuthenticationPrincipal PrincipalDetails user) {
        boolean result = false;

        if (Objects.nonNull(user))
            result = likeService.addLike(user, commentId);

        return result ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
