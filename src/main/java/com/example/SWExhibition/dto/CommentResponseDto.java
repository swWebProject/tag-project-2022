package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Comments;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponseDto {

    private Long commentID;
    private String comment;
    private String nickname;
    private String movieCd;
    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

    /* Entity -> Dto*/
    public CommentResponseDto(Comments comment) {
        this.commentID = comment.getCommentID();
        this.comment = comment.getComment();
        this.nickname = comment.getUser().getNickname();
        this.movieCd = comment.getMovieCd().getMovieCd();
        this.createDate = comment.getCreateDate();
    }
}