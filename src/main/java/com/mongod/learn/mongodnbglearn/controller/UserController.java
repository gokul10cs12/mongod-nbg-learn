package com.mongod.learn.mongodnbglearn.controller;

import com.mongod.learn.mongodnbglearn.Services.UserService;
import com.mongod.learn.mongodnbglearn.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable String userId){
        Optional<User> user = userService.getUserInfo(userId);
        return new ResponseEntity<>( user.get(), HttpStatus.OK);
    }


}
