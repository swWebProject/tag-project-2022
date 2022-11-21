package com.example.SWExhibition.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
@DynamicUpdate  // 변경된 값만 업데이트
public class Comments extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentID; // 댓글 ID

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user; // 유저 ID

    @ManyToOne
    @JoinColumn(name = "movies_movieCd")
    private Movies movieCd;   // 영화 코드

    @Column(nullable = false)
    private String comment; // 댓글 내용

    @ManyToOne
    @JoinColumn(columnDefinition = "INT default 0")
    private Likes likeCnt;  // 좋아요 수

    @Builder
    public Comments(Long commentID, Users user, Movies movieCd, String comment, Likes likeCnt) {
        this.commentID = commentID;
        this.user = user;
        this.movieCd = movieCd;
        this.comment = comment;
        this.likeCnt = likeCnt;
    }

}
