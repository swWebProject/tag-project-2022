package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Comments;
import com.example.SWExhibition.entity.Likes;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class CommentRequestDto {

        private Long commentID;
        private Users user;
        private Movies movie;
        private String comment;
        private Likes likeCnt;

        private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

        public Comments toEntity() {
                Comments comments = Comments.builder()
                        .commentID(commentID)
                        .user(user)
                        .movieCd(movie)
                        .comment(comment)
                        .likeCnt(likeCnt)
                        .createDate(createDate)
                        .build();

                return comments;
        }
}
