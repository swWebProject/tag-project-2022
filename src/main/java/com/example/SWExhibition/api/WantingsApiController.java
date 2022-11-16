package com.example.SWExhibition.api;

import com.example.SWExhibition.dto.WantingsDto;
import com.example.SWExhibition.entity.Wantings;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.WantingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WantingsApiController {

    private final WantingsService wantingsService;

    // 보고 싶은 영화 추가
    @PostMapping("/api/post/wanting")
    public ResponseEntity<Wantings> postWanting(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody WantingsDto dto) {
        Wantings wanting = wantingsService.updateWanting(principalDetails, dto);    // 세션 정보와 보내준 값으로 보고 싶은 영화 업데이트 ( 추가 또는 삭제)

        return ResponseEntity.status(HttpStatus.OK).body(wanting);
    }

    // 유저의 보고 싶은 영화 목록
    @GetMapping("/api/get/wanting")
    public List<Wantings> getWantingList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Wantings> wantingsList = wantingsService.showWantings(principalDetails);   // 보고 싶은 영화 목록

        return wantingsList;
    }

}
