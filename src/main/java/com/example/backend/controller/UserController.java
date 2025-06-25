package com.example.backend.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Repository.UserRepository;
import com.example.backend.model.User;

@RestController
@RequestMapping("/api/users")
@CrossOrigin (origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user){
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @PutMapping("/{username}")
    public String updateUser(@RequestBody User user, @PathVariable String username){
        Optional<User> userUpdate = userRepository.findByUsername(username);

            if(userUpdate.isPresent()){
                User existingUser = userUpdate.get();
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                existingUser.setEmail(user.getEmail());
                existingUser.setUpdatedAt(LocalDateTime.now());

                userRepository.save(existingUser);
                return "User update successfully!";
            }else{
                return "Not updated";
            }
    }
}
