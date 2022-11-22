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
}
