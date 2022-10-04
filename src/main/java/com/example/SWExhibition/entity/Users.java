package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Users {
    @Id
    private String id;  // 유저 ID

    @Column(nullable = false)
    private String passward;    // 비밀번호

    @Column(nullable = false, unique = true)
    private String nickname;    // 닉네임

    // 데이터 수정
    public void patch(Users user) {
        if (this.id != user.getId())
            throw new IllegalArgumentException("계정 수정 실패! 잘못된 ID가 입력되었습니다.");

        // 비밀번호 변경
        if (user.getPassward() != null)
            this.passward = user.getPassward();

        // 닉네임 변경
        if (user.getNickname() != null)
            this.nickname = user.getNickname();
    }
}
