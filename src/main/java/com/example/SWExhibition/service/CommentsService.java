package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.CommentDto;
import com.example.SWExhibition.entity.Comments;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.repository.CommentsRepository;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final UsersRepository usersRepository;
    private final MoviesRepository moviesRepository;

    @Transactional
    public Object commentsSave(String nickname,String movieCd, CommentDto.CommentsRequestDto dto) {
        Users users = usersRepository.findByNickname(nickname);
        Movies movies = moviesRepository.findByMovieCd(movieCd);

        dto.setUser(users);
        dto.setMovie(movies);

        Comments comment = dto.toEntity();
        commentsRepository.save(comment);

        return dto.getCommentID();

    }
}
