package com.example.quest.services;

import com.example.quest.entities.User;
import com.example.quest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        userRepository.save(foundUser);
        return foundUser;

    }

    public void deleteUser(Long userId){
        this.userRepository.deleteById(userId);
    }
}
