package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.model.CustomEventModel;
import com.mongod.learn.mongodnbglearn.model.User;
import com.mongod.learn.mongodnbglearn.repository.ExpenseRepository;
import com.mongod.learn.mongodnbglearn.repository.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
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

    @EventListener
    public void testListener(CustomEventModel customEventModel){
        System.out.println("UserService: got the message from userService:"+ customEventModel.getMessage());
    }
    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());

        return userRepository.save(user);
    }
}
