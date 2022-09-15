package com.mongod.learn.mongodnbglearn.controller;

import com.mongod.learn.mongodnbglearn.Services.ExpenseService;
import com.mongod.learn.mongodnbglearn.model.Expense;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping()
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense){
       return new ResponseEntity<>(expenseService.addExpense(expense), HttpStatus.OK);
    }
    @DeleteMapping("/{expenseName}")
    public void removeExpense(@PathVariable String expenseName ){

        if (expenseService.removeExpenseByName(expenseName)){
            System.out.println("removed Succeeded");
        } else {
            System.out.println("failed");
        }

    }
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(){

        return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK);

    }
    public void updateExpenses(){}
    @GetMapping("/{name}")
    public ResponseEntity<Expense> getExpenseByName(@PathVariable String name){
        return new ResponseEntity<>(expenseService.getExpenseByName(name), HttpStatus.OK);
    }
    public void removeExpenseByName(){}

}
