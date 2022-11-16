package com.example.SWExhibition.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    //좋야요 한 유저 아이디
    @ManyToOne
    @JoinColumn(name = "users_Id")
    private Users userId;

    //좋아요 한 댓글
    @ManyToOne
    @JoinColumn(name = "comments_commentId")
    private Comments comments;

    @Builder
    public Likes(Long likeId, Users userId, Comments comments) {
        this.likeId = likeId;
        this.userId = userId;
        this.comments = comments;
    }

}
