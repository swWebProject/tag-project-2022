package com.example.SWExhibition.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "participants")
@NoArgsConstructor
@Getter
@ToString
@DynamicUpdate  // 변경된 값만 업데이트
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 객체 id

    @Column(nullable = false, unique = true)
    private String peopleCd;  // 영화인 코드

    @Column(nullable = false)
    private String peopleNm;    // 이름

    @Column
    private String peopleNmEn;  // 영어 이름

    @Column
    private String sex; // 성별

    @Column(nullable = false)
    private String repRoleNm;   // 영화인 분류명 (배우 or 감독)

    @Column(columnDefinition = "INT default 0")
    private Long likeCnt;  // 좋아요 수

    @Builder
    public Participants(Long id, String peopleCd, String peopleNm, String peopleNmEn, String sex, String repRoleNm, Long likeCnt) {
        this.id = id;
        this.peopleCd = peopleCd;
        this.peopleNm = peopleNm;
        this.peopleNmEn = peopleNmEn;
        this.sex = sex;
        this.repRoleNm = repRoleNm;
        this.likeCnt = likeCnt;
    }
}