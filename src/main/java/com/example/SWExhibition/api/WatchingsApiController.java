package com.example.SWExhibition.api;

import com.example.SWExhibition.dto.WatchingsDto;
import com.example.SWExhibition.entity.Watchings;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.WatchingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WatchingsApiController {

    private final WatchingsService watchingsService;

    // 보는 중인 영화 추가
    @PostMapping("/api/post/watching")
    public ResponseEntity<Integer> postWanting(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody WatchingsDto dto) {
        // 로그인 상태가 아니면 에러 발생
        if ( principalDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 필요!");
        }

        Integer watching = watchingsService.updateWatching(principalDetails, dto);    // 세션 정보와 보내준 값으로 보고 싶은 영화 업데이트 ( 추가 또는 삭제)

        return ResponseEntity.status(HttpStatus.OK).body(watching); // 1이면 보고 싶은 영화, 0이면 보고 싶지 않은 영화 ( DB에서 삭제)
    }

    // 유저의 보고 싶은 영화 목록
    @GetMapping("/api/get/watching")
    public List<Watchings> getWantingList(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        return watchingsService.showWatchings(principalDetails);
    }

    // 보는 중인 영화에 대한 참, 거짓
    @GetMapping("/api/get/watching/movieCd={movieCd}")
    public ResponseEntity<Boolean> getWatching(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String movieCd) {
        Boolean existWanting = watchingsService.show(principalDetails, movieCd);

        return ResponseEntity.status(HttpStatus.OK).body(existWanting);
    }
}
