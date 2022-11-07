package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.model.User;
import com.mongod.learn.mongodnbglearn.repository.ExpenseRepository;
import com.mongod.learn.mongodnbglearn.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public UserService(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    public Optional<User> getUserInfo(String userId){
        return userRepository.findById(userId);
    }

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());

        return userRepository.save(user);
    }
}
