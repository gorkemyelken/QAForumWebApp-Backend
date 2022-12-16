package com.example.quest.controllers;

import com.example.quest.entities.Comment;
import com.example.quest.entities.Post;
import com.example.quest.entities.User;
import com.example.quest.requests.CommentCreateRequest;
import com.example.quest.requests.CommentUpdateRequest;
import com.example.quest.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllCommentsWithParams(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId){
        return this.commentService.getAllCommentsWithParams(userId,postId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest commentCreateRequest){
        return commentService.createOneComment(commentCreateRequest);

    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId) {
        return commentService.getOneCommentById(commentId);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequest commentUpdateRequest){
        return this.commentService.updateOneComment(commentId,commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneCommentById(@PathVariable Long commentId){
        this.commentService.deleteOneCommentById(commentId);
    }
}
