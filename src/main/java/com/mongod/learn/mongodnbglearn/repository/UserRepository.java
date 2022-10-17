package com.mongod.learn.mongodnbglearn.repository;

import com.mongod.learn.mongodnbglearn.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
