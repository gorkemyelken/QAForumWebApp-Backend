package com.example.quest.services;

import com.example.quest.entities.Comment;
import com.example.quest.entities.Like;
import com.example.quest.entities.User;
import com.example.quest.repositories.CommentRepository;
import com.example.quest.repositories.LikeRepository;
import com.example.quest.repositories.PostRepository;
import com.example.quest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public UserRepository userRepository;
    public LikeRepository likeRepository;
    public CommentRepository commentRepository;
    public PostRepository postRepository;
    @Autowired
    public UserService(UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(User newUser){
        return this.userRepository.save(newUser);
    }

    public User getOneUser(Long userId){
        return this.userRepository.findById(userId).orElse(null);
    }

    public User updateUser(Long userId, User newUser){
        Optional<User> user =  this.userRepository.findById(userId);
        User foundUser = user.get();
        foundUser.setUserName(newUser.getUserName());
        foundUser.setPassword(newUser.getPassword());
        foundUser.setAvatar(newUser.getAvatar());
        userRepository.save(foundUser);
        return foundUser;

    }

    public void deleteUser(Long userId){
        this.userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if(postIds.isEmpty())
            return null;
        List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
        List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
    }
}
