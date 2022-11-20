package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Comments;
import com.example.SWExhibition.entity.Likes;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CommentDto {

    @Data
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsRequestDto {
        private Long commentID;
        private Users user;
        private Movies movie;
        private String comment;
        private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private Likes likeCnt;

        public Comments toEntity() {
            Comments comments = Comments.builder()
                    .commentID(commentID)
                    .user(user)
                    .movieCd(movie)
                    .comment(comment)
                    .likeCnt(likeCnt)
                    .build();

            return comments;
        }
    }

    @Getter
    public static class CommentsResponseDto {
        private Long commentId;
        private String nickname;
        private String comment;
        private String createDate;
        private String modifiedDate;
        private Movies movie;
        private Likes likeCnt;


        public CommentsResponseDto(Comments comments) {
            this.commentId = comments.getCommentID();
            this.nickname = comments.getUser().getNickname();
            this.comment = comments.getComment();
            this.createDate = comments.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
            this.modifiedDate = comments.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
            this.movie = comments.getMovieCd();
            this.likeCnt = comments.getLikeCnt();
        }
    }

}
