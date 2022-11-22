package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentID;
    private String comment;
    private Users user;
    private Movies movie;
    private Boolean isWriter;
}