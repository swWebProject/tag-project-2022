package com.example.SWExhibition.api;

import com.example.SWExhibition.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserOverlopCheck {

    private final UsersService usersService;

    // userId 중복 결과 api
    @GetMapping("/api/exist/{userId}")
    public ResponseEntity<Boolean> checkUserId(@PathVariable String userId) {
        return ResponseEntity.ok(usersService.userIdOverlopCheck(userId));
    }

    // nickname 중복 결과 api
    @GetMapping("/api/exist/{nickname}")
    public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(usersService.userIdOverlopCheck(nickname));
    }
}
