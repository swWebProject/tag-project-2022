package com.example.SWExhibition.api;

import com.example.SWExhibition.dto.UsersDto;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.security.PrincipalDetails;
import com.example.SWExhibition.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UsersService usersService;

    // 유저 정보 수정
    @PutMapping("/api/patch/user")
    public ResponseEntity<Users> update(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UsersDto dto) {
        Users updated = usersService.updateUser(principalDetails, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 유저 정보 삭제
    @DeleteMapping("/api/delete/user")
    public ResponseEntity<Users> delete(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Users deleted = usersService.deleteUser(principalDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}
