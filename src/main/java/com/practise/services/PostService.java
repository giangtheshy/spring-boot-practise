package com.practise.services;

import com.practise.dto.PostRequest;
import com.practise.exceptions.SpringException;
import com.practise.models.Post;
import com.practise.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
  private final PostRepository postRepository;

  public Post getPost(Long id) {
    return postRepository
        .findById(id)
        .orElseThrow(() -> new SpringException("Post can be not found by id"));
  }public Post getPostTest(Long id) {
    return postRepository
        .findById(id)
        .orElseThrow(() -> new SpringException("Post can be not found by id"));
  }

  @Secured({"ROLE_ADMIN", "ROLE_USER"})
  public Post createPost(PostRequest postRequest) {
    Post post =
        Post.builder()
            .title(postRequest.getTitle())
            .description(postRequest.getDescription())
            .build();
    return postRepository.save(post);
  }

  @Secured("ROLE_ADMIN")
  public void deletePost(Long id) {
    postRepository.deleteById(id);
  }
}
