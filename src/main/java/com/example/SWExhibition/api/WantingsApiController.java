package com.example.SWExhibition.api;

import com.example.SWExhibition.dto.WantingsDto;
import com.example.SWExhibition.entity.Wantings;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.WantingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WantingsApiController {

    private final WantingsService wantingsService;

    // 보고 싶은 영화 추가
    @PostMapping("/api/post/wanting")
    public ResponseEntity<Integer> postWanting(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody WantingsDto dto) {
        // 로그인 상태가 아니면 에러 발생
        if ( principalDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 필요!");
        }

        Integer wanting = wantingsService.updateWanting(principalDetails, dto);    // 세션 정보와 보내준 값으로 보고 싶은 영화 업데이트 ( 추가 또는 삭제)

        return ResponseEntity.status(HttpStatus.OK).body(wanting);  // 1이면 보고 싶은 영화, 0이면 보고 싶지 않은 영화 ( DB에서 삭제)
    }

    // 유저의 보고 싶은 영화 목록
    @GetMapping("/api/get/wanting")
    public List<Wantings> getWantingList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return wantingsService.showWantings(principalDetails);
    }

    // 보고 싶은 영화에 대한 참, 거짓
    @GetMapping("/api/get/wanting/movieCd={movieCd}")
    public ResponseEntity<Boolean> getWanting(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String movieCd) {
        // 로그인 상태가 아니면 에러 발생
        if ( principalDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 필요!");
        }

        Boolean existWanting = wantingsService.show(principalDetails, movieCd);
        
        return ResponseEntity.status(HttpStatus.OK).body(existWanting);
    }

}
