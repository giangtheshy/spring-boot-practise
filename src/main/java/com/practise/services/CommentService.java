package com.practise.services;

import com.practise.dto.CommentRequest;
import com.practise.exceptions.SpringException;
import com.practise.models.Comment;
import com.practise.models.Post;
import com.practise.repositories.CommentRepository;
import com.practise.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  @Secured("ROLE_USER")
  public void createComment(CommentRequest commentRequest) {
     commentRepository.addComment(commentRequest.getMessage(),commentRequest.getPost_id());

  }
}
