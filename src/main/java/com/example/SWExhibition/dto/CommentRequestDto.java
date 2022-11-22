package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Comments;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentRequestDto {

        private Long commentID;
        private Users user;
        private Movies movie;
        private String comment;

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
