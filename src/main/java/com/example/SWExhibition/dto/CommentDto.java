package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Comments;
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

        public Comments toEntity() {
            Comments comments = Comments.builder()
                    .commentID(commentID)
                    .user(user)
                    .movieCd(movie)
                    .comment(comment)
                    .build();

            return comments;
        }
    }

}
