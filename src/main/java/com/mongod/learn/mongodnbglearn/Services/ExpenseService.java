package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.Exception.ExpenseNotFound;
import com.mongod.learn.mongodnbglearn.model.Expense;
import com.mongod.learn.mongodnbglearn.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense){
        expense.setId(UUID.randomUUID().toString());
        return expenseRepository.insert(expense);
    }
    public void removeExpense(){}
    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }
    public Expense updateExpenses(Expense expense) {
        if(expenseRepository.findById(expense.getId()).isPresent()) {
            return expenseRepository.save(expense);
        }
        return  null;

    }
    public Expense getExpenseByName(String name){
        return expenseRepository.findExpenseByExpenseName(name);
    }
    public void removeExpenseByName(String expenseName){
        expenseRepository.deleteByExpenseName(expenseName);
    }

}
