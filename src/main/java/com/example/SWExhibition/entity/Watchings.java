package com.example.SWExhibition.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Watchings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 보고싶은 영화 ID

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user; // 유저 ID

    @ManyToOne
    @JoinColumn(name = "movies_movieCd")
    private Movies movie; // 영화 코드

    @Column(nullable = false)
    private String date; // 등록된 날짜와 시간

    @Builder
    public Watchings(Long id, Users user, Movies movie, String date) {
        this.id = id;
        this.user = user;
        this.movie = movie;
        this.date = date;
    }
}
