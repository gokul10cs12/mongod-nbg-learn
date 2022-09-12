package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void addExpense(){}
    public void removeExpense(){}
    public void seeAllExpenses(){}
    public void getExpenseByName(){}
    public void removeExpenseByName(){}

}
