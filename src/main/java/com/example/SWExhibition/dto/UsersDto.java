package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Users;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsersDto {

    private Long id;    // id

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-z0-9]{5,20}$",
            message = "아이디는 5 ~ 20자리까지로 영문, 숫자만 가능합니다.")
    private String userId;  // User ID

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;    // 비밀번호

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$",
            message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String nickname;    // 닉네임

    private String role;    // 권한

    public UsersDto(Users user) {
    }

    // DTO -> Entity
    public Users toEntity() {
        return Users.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .nickname(nickname)
                .role(role)
                .build();
    }

    @Builder
    public UsersDto(Long id, String userId, String password, String nickname, String role) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

}
