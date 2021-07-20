package com.practise.controllers;

import com.practise.dto.PostRequest;
import com.practise.models.Post;
import com.practise.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
  private final PostService postService;

  @GetMapping("/{id}")
  @Secured({"ROLE_USER"})
  public ResponseEntity<Post> getPost(@PathVariable Long id) {
    return ResponseEntity.status(OK).body(postService.getPostTest(id));
  }



  @PostMapping("/")
  public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {
    return ResponseEntity.status(CREATED).body(postService.createPost(postRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return ResponseEntity.status(OK).body("Deleted post!");
  }
}
