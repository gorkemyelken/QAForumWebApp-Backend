package com.example.quest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentService commentService;

    @Autowired
    public CommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
