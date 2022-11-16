package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Comments;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long commentID;
    private String comment;
    private String nickname;
    private Long movieId;

    /* Entity -> Dto*/
    public CommentResponseDto(Comments comment) {
        this.commentID = comment.getCommentID();
        this.comment = comment.getComment();
        this.nickname = comment.getUser().getNickname();
        this.movieId = comment.getMovie().getId();
    }
}