package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@DynamicUpdate  // 변경된 값만 업데이트
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long peopleCd;  // 영화인 코드

    @Column(nullable = false)
    private String peopleNm;    // 이름

    @Column(nullable = false)
    private String repRoleNm;   // 분야 (감독 or 배우)

    @Column(columnDefinition = "INT default 0")
    private Long like;  // 좋아요 수
}