package com.example.SWExhibition.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
@DynamicUpdate  // 변경된 값만 업데이트
public class Ratings extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 평점 ID

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user; // 유저 ID

    @ManyToOne
    @JoinColumn(name = "movies_movieCd")
    private Movies movie;   // 영화 코드

    @Column
    private Long rating;   // 평점

    @Builder
    public Ratings(Long id, Users user, Movies movie, Long rating) {
        this.id = id;
        this.user = user;
        this.movie = movie;
        this.rating = rating;
    }
}
