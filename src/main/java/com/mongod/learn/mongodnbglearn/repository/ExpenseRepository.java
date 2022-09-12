package com.mongod.learn.mongodnbglearn.repository;

import com.mongod.learn.mongodnbglearn.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ExpenseRepository extends MongoRepository<Expense, String > {
    Expense findExpenseByExpenseName(String name);
}
