package com.practise.controllers;

import com.practise.dto.CommentRequest;
import com.practise.models.Comment;
import com.practise.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<String> createComment(@RequestBody CommentRequest commentRequest){
        commentService.createComment(commentRequest);
        return ResponseEntity.status(CREATED).body("Add comment successfully");
    }
}
