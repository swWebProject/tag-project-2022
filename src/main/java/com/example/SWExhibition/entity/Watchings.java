package com.example.SWExhibition.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Watchings extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 보고싶은 영화 ID

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user; // 유저 ID

    @ManyToOne
    @JoinColumn(name = "movies_movieCd")
    private Movies movie; // 영화 코드

    @Builder
    public Watchings(Long id, Users user, Movies movie) {
        this.id = id;
        this.user = user;
        this.movie = movie;
    }
}
