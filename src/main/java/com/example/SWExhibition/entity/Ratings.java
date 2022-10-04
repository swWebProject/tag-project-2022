package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@DynamicUpdate  // 변경된 값만 업데이트
public class Ratings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingID;  // 평점 ID

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user; // 유저 ID

    @ManyToOne
    @JoinColumn(name = "movies_movieCd")
    private Movies movie;   // 영화 코드

    @Column(columnDefinition = "float(2,1)")
    private Float rating;   // 평점

    @Column(nullable = false)
    private LocalDateTime date; // 등록된 날짜와 시간
}
