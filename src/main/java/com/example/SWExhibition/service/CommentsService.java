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

@RequiredArgsConstructor
@Service
public class CommentsService {
    private final CommentsRepository commentRepository;
    private final UsersRepository userRepository;
    private final MoviesRepository moviesRepository;

    /* CREATE */
    @Transactional
    public Long commentSave(String nickname, Long id, CommentRequestDto dto) {
        Users user = userRepository.findByNickname(nickname);
        Movies movies = moviesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));

        dto.setUser(user);
        dto.setMovie(movies);

        Comments comment = dto.toEntity();
        commentRepository.save(comment);

        return dto.getCommentID();
    }

    /* UPDATE */
    @Transactional
    public void update(Long id, CommentRequestDto dto) {
        Comments comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        comment.update(dto.getComment());
    }

    /* DELETE */
    @Transactional
    public void delete(Long id) {
        Comments comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        commentRepository.delete(comment);
    }
}
