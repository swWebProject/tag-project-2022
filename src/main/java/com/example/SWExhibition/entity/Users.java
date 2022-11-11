package com.example.SWExhibition.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 객체 id

    @Column(nullable = false, unique = true)
    private String userId;  // 유저 ID

    @Column(nullable = false)
    private String password;    // 비밀번호

    @Column(nullable = false, unique = true)
    private String nickname;    // 닉네임

    @Column(nullable = false)
    private String role;    // 권한

    @Builder
    public Users(Long id, String userId, String password, String nickname, String role) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    // 데이터 수정
    public void patch(Users user) {
        if (this.userId != user.getUserId())
            throw new IllegalArgumentException("계정 수정 실패! 잘못된 ID가 입력되었습니다.");

        // 비밀번호 변경
        if (user.getPassword() != null)
            this.password = user.getPassword();

        // 닉네임 변경
        if (user.getNickname() != null)
            this.nickname = user.getNickname();
    }


}
