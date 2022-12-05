package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.model.Expense;
import com.mongod.learn.mongodnbglearn.repository.ExpenseRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public ExpenseService(ExpenseRepository expenseRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.expenseRepository = expenseRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Expense addExpense(Expense expense){
        expense.setId(UUID.randomUUID().toString());
        return expenseRepository.insert(expense);
    }
    public void removeExpense(){}
    public List<Expense> getAllExpenses(){
        applicationEventPublisher.publishEvent("String it is");
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
