package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.CommentRequestDto;
import com.example.SWExhibition.entity.Comments;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.repository.CommentsRepository;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private final CommentsRepository commentRepository;
    private final UsersRepository userRepository;
    private final MoviesRepository moviesRepository;


    /* CREATE */
    @Transactional
    public Movies commentSave(String nickname, String movieCd, CommentRequestDto dto) {
        Users user = userRepository.findByNickname(nickname);
        Movies movies = moviesRepository.findByMovieCd(movieCd);
        if(movies == null) {
            return null;
        }
        dto.setUser(user);
        dto.setMovie(movies);
        
        Comments comment = dto.toEntity();
        commentRepository.save(comment);

        return dto.getMovie();
    }

    public List<Comments> show(Movies movies) {
        return commentRepository.findByMovieCd(movies);
    }

    /* DELETE comment id로 찾아 삭제*/
    @Transactional
    public void deleteComment(Long id) {
        Comments comment = commentRepository.findById(id).orElse(null);
        if(comment == null) {
            throw new IllegalArgumentException("댓글이 삭제되었거나 존재하지 않습니다");
        }

        commentRepository.delete(comment);
    }
}
