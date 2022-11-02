package com.example.SWExhibition.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@ToString
@Getter
public class Users {
    @Id
    private String id;  // 유저 ID

    @Column(nullable = false)
    private String password;    // 비밀번호

    @Column(nullable = false, unique = true)
    private String nickname;    // 닉네임

    @Builder
    public Users(String id, String password, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }

    // 데이터 수정
    public void patch(Users user) {
        if (this.id != user.getId())
            throw new IllegalArgumentException("계정 수정 실패! 잘못된 ID가 입력되었습니다.");

        // 비밀번호 변경
        if (user.getPassword() != null)
            this.password = user.getPassword();

        // 닉네임 변경
        if (user.getNickname() != null)
            this.nickname = user.getNickname();
    }


}
