package com.example.quest.controllers;

import com.example.quest.entities.User;
import com.example.quest.responses.UserResponse;
import com.example.quest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userService.createUser(newUser);
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId){
        return new UserResponse(userService.getOneUser(userId));
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, User user){
        return this.userService.updateUser(userId,user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(Long userId){
        this.userService.deleteUser(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId){
        return userService.getUserActivity(userId);
    }
}
