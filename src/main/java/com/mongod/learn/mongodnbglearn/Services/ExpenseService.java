package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.model.Expense;
import com.mongod.learn.mongodnbglearn.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense){
        expense.setId(UUID.randomUUID().toString());
        System.out.println(expense.getExpenseName());
        return expenseRepository.insert(expense);
    }
    public void removeExpense(){}
    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }
    public void updateExpenses(){}
    public Expense getExpenseByName(String name){
        return expenseRepository.findExpenseByExpenseName(name);
    }
    public boolean removeExpenseByName(String expenseName){
        return expenseRepository.deleteByExpenseName(expenseName);
    }

}
