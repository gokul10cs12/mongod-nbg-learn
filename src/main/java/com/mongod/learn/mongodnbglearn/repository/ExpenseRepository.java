package com.mongod.learn.mongodnbglearn.repository;

import com.mongod.learn.mongodnbglearn.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.Optional;


public interface ExpenseRepository extends MongoRepository<Expense, String > {
    Expense findExpenseByExpenseName(String name);
    void deleteByExpenseName(String name);
}
