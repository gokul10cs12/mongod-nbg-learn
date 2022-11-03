package com.mongod.learn.mongodnbglearn.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collation = "user_details")
public class User {
    @Id
    private String id;
    @Field(name = "username")
    private String username;
    @Field(name = "password")
    private String password;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'_id': ?#{#self.id}'}")
    private List<Expense> expenses;

    public User(String username, String password, List<Expense> expenseList) {
        this.username = username;
        this.password = password;
        this.expenseList = expenseList;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    private List<Expense> expenseList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }
}
