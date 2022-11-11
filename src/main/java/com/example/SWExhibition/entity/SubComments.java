package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class SubComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCommentID;  // 답글 ID

    @ManyToOne
    @JoinColumn(name = "comments_commentID")
    private Comments commnet;   // 댓글 ID

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user; // 유저 ID

    @Column(nullable = false)
    private String subComment;  // 답글 내용

    // 데이터 수정
    public void patch(SubComments subComment) {
        if (this.subCommentID != subComment.subCommentID)
            throw new IllegalArgumentException("답글 수정 실패! 잘못된 ID가 입력되었습니다.");

        // 답글 내용 수정
        if (subComment.subComment != null)
            this.subComment = subComment.subComment;
    }
}
