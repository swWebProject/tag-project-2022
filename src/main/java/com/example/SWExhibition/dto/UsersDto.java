package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Users;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsersDto {

    private String id;  // User ID
    private String password;    // 비밀번호
    private String nickname;    // 닉네임

    /// DTO -> Entity
    public Users toEntity() {
        return Users.builder()
                .id(id)
                .password(password)
                .nickname(nickname)
                .build();
    }

    @Builder
    public UsersDto(String id, String password, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }

}
