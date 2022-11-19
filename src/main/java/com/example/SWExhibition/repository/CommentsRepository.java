package com.example.SWExhibition.repository;

import com.example.SWExhibition.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    Comments findBymovieCd(String movieCd);
}
