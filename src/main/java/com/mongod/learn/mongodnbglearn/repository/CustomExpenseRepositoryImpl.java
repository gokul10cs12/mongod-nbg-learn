package com.mongod.learn.mongodnbglearn.repository;

import com.mongod.learn.mongodnbglearn.model.Expense;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class CustomExpenseRepositoryImpl implements CustomExpenseRepository {

    private final MongoTemplate mongoTemplate;

    public CustomExpenseRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Query query = new Query(Criteria.where("name").is(expense.getExpenseName()));
        Update update = new Update();
        update.set("amount", expense.getExpenseAmount()).set("category", expense.getExpenseCategory());
        FindAndModifyOptions findAndModifyOptions = FindAndModifyOptions.options().returnNew(true);
        Expense result = mongoTemplate.findAndModify(query, update, findAndModifyOptions, Expense.class);

        return result;
    }
}
