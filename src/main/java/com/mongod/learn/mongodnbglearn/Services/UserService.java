package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.model.User;
import com.mongod.learn.mongodnbglearn.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserInfo(String userId){
        return userRepository.findById(userId);
    }
}
