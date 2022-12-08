package com.mongod.learn.mongodnbglearn.repository;

import com.mongod.learn.mongodnbglearn.model.Expense;
import com.mongod.learn.mongodnbglearn.model.ExpenseCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface ExpenseRepository extends MongoRepository<Expense, String > {
    Expense findExpenseByExpenseName(String name);
    void deleteByExpenseName(String name);


    //here the expenseCategory is the argument to be passed to the method, and the fields denoted what all need tobe captured
    // to the return
    @Query(value = "{expenseCategory: '?0'}", fields = "{expenseName:  1}")
    List<Expense> myEntertainmentExpense(ExpenseCategory expenseCategory);
}
