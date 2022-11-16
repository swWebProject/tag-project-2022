package com.example.SWExhibition.api;

import com.example.SWExhibition.dto.WatchingsDto;
import com.example.SWExhibition.entity.Watchings;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.WatchingsService;
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
public class WatchingsApiController {

    private final WatchingsService watchingsService;

    // 보는 중인 영화 추가
    @PostMapping("/api/post/watching")
    public ResponseEntity<Watchings> postWanting(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody WatchingsDto dto) {
        Watchings watching = watchingsService.updateWatching(principalDetails, dto);    // 세션 정보와 보내준 값으로 보고 싶은 영화 업데이트 ( 추가 또는 삭제)

        return ResponseEntity.status(HttpStatus.OK).body(watching);
    }

    // 유저의 보고 싶은 영화 목록
    @GetMapping("/api/get/watching")
    public List<Watchings> getWantingList(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        return watchingsService.showWatchings(principalDetails);
    }
}
