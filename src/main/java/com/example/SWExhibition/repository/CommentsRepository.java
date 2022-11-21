package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Comments;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<Comments> findByMovieCd(Movies movieCd);
    Comments findByMovieCdAndUser(Movies movieCd, Users users);
}
