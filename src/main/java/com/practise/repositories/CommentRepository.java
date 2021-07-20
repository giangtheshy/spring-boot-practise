package com.practise.repositories;

import com.practise.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Modifying
  @Query(value = "INSERT INTO comment (message, post_id) VALUES ( ?1, ?2)",nativeQuery = true)
  void addComment(String message, Long postId);
}
