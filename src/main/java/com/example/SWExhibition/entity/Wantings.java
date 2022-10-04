package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Wantings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wantingID; // 보고싶은 영화 ID

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user; // 유저 ID

    @ManyToOne
    @JoinColumn(name = "movies_movieCd")
    private Movies movie; // 영화 코드

    @Column(nullable = false)
    private LocalDateTime date; // 등록된 날짜와 시간
}
