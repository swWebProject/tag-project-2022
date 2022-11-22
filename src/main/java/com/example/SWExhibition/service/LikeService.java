package com.example.SWExhibition.service;

import com.example.SWExhibition.entity.Comments;
import com.example.SWExhibition.repository.CommentsRepository;
import com.example.SWExhibition.repository.LikeRepository;
import com.example.SWExhibition.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final CommentsRepository commentsRepository;

    public boolean addLike(@AuthenticationPrincipal PrincipalDetails user, Long commentId) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow();

        //중복 좋아요 방지
        if (isNotAlreadyLike(user, comments)) {
            likeRepository.save(new Likes(comments, user));
            return true;
        }
        return false;
    }

    public void cancelLike(@AuthenticationPrincipal PrincipalDetails user, Long commentId) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow();
        Likes like = likeRepository.findByMemberAndComments(user, comments).orElseThrow();
        likeRepository.delete(like);
    }

    public List<String> count(Long commentId, @AuthenticationPrincipal PrincipalDetails user) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow();

        Integer likeCount = likeRepository.countByComments(comments).orElse(0);

        List<String> resultData =
                new ArrayList<>(Arrays.asList(String.valueOf(likeCount)));

        if (Objects.nonNull(user)) {
            resultData.add(String.valueOf(isNotAlreadyLike(user, comments)));
            return resultData;
        }

        return resultData;
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(@AuthenticationPrincipal PrincipalDetails user, Comments comments) {
        return likeRepository.findByMemberAndComments(user, comments).isEmpty();
    }

}