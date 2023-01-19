package com.example.quest.services;

import com.example.quest.entities.Post;
import com.example.quest.entities.User;
import com.example.quest.entities.Like;
import com.example.quest.repositories.PostRepository;
import com.example.quest.requests.PostCreateRequest;
import com.example.quest.requests.PostUpdateRequest;
import com.example.quest.responses.LikeResponse;
import com.example.quest.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private LikeService likeService;
    private UserService userService;
    @Autowired
    public PostService(PostRepository postRepository, UserService userService,@Lazy LikeService likeService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeService = likeService;
    }
    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()){
            list = postRepository.findByUserId(userId.get());
        }else
        list = postRepository.findAll();
        return list.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null),Optional.of(p.getId()));
            return new PostResponse(p, likes);}).collect(Collectors.toList());
    }

    public Post getOnePostById(Long postId) {
        return this.postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUser(newPostRequest.getUserId());
        if(user==null)
            return null;
        Post toSave = new Post();
        toSave.setId(newPostRequest.getId());
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        return postRepository.save(toSave);
    }

    public void deleteOnePostById(Long postId) {
        this.postRepository.deleteById(postId);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()) {
            Post toUpdate = post.get();
            toUpdate.setText(postUpdateRequest.getText());
            toUpdate.setTitle(postUpdateRequest.getTitle());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }
}
