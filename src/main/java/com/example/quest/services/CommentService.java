package com.example.quest.services;

import com.example.quest.entities.Comment;
import com.example.quest.entities.Post;
import com.example.quest.entities.User;
import com.example.quest.repositories.CommentRepository;
import com.example.quest.requests.CommentCreateRequest;
import com.example.quest.requests.CommentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllCommentsWithParams(Optional<Long> userId, Optional<Long> postId){
        if(userId.isPresent() && postId.isPresent()) {
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()) {
            return commentRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        }else
            return commentRepository.findAll();
    }

    public Comment getOneCommentById(Long commentId){
        return this.commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest commentCreateRequest){
        User user = this.userService.getOneUser(commentCreateRequest.getUserId());
        Post post= this.postService.getOnePostById(commentCreateRequest.getPostId());
        if(user != null && post !=null){
            Comment commentToSave = new Comment();
            commentToSave.setId(commentCreateRequest.getId());
            commentToSave.setText(commentCreateRequest.getText());
            commentToSave.setUser(user);
            commentToSave.setPost(post);
            commentToSave.setCreateDate(new Date());
            return commentRepository.save(commentToSave);
        }
        else return null;

    }

    public Comment updateOneComment(Long commentId, CommentUpdateRequest commentUpdateRequest){
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(commentUpdateRequest.getText());
            return commentRepository.save(commentToUpdate);
        }
        else return null;
    }

    public void deleteOneCommentById(Long commentId){
        this.commentRepository.deleteById(commentId);
    }
}
