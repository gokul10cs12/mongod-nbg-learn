package com.mongod.learn.mongodnbglearn.repository;

import com.mongod.learn.mongodnbglearn.model.Expense;
import org.springframework.stereotype.Repository;

public interface CustomExpenseRepository {

    Expense updateExpense(Expense expense);
}
