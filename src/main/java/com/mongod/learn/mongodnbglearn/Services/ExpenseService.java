package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.model.Expense;
import com.mongod.learn.mongodnbglearn.model.ExpenseCategory;
import com.mongod.learn.mongodnbglearn.repository.CustomExpenseRepository;
import com.mongod.learn.mongodnbglearn.repository.ExpenseRepository;
import com.mongodb.client.model.ValidationAction;
import org.bson.Document;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.BulkOperationException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final CustomExpenseRepository customExpenseRepository;

    private final MongoTemplate mongoTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    public ExpenseService(ExpenseRepository expenseRepository, CustomExpenseRepository customExpenseRepository, MongoTemplate mongoTemplate, ApplicationEventPublisher applicationEventPublisher) {
        this.expenseRepository = expenseRepository;
        this.customExpenseRepository = customExpenseRepository;
        this.mongoTemplate = mongoTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Expense addExpense(Expense expense){
        expense.setId(UUID.randomUUID().toString());
        List<Expense> entertainmentExpenses= expenseRepository.myEntertainmentExpense(ExpenseCategory.ENTERTAINMENT);
        return expenseRepository.insert(expense);
    }
    public void removeExpense(){}
    public List<Expense> getAllExpenses(){
        ExpenseCategory []categories = new ExpenseCategory[]{ExpenseCategory.ENTERTAINMENT, ExpenseCategory.UTILITIES};

        applicationEventPublisher.publishEvent("String it is");
        Set<String> ids = new HashSet<>();
        Criteria criteria = where("category").in(categories);
        Query query = new Query();
        query.addCriteria(criteria);

        //bulk operation
        BulkOperations bulkOperationException = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, "expense");
        bulkOperationException.remove(new Query(where("_id").in(ids)));
        bulkOperationException.execute();


        //aggregation

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(where("category").ne(null)),
                Aggregation.group("category").count().as("count"),
                Aggregation.match(where("count").gt(1)),
                Aggregation.project( "count").andExpression("_id").as("category"));

        AggregationResults<Output> aggregationResults = mongoTemplate.aggregate(aggregation,"expense", Output.class );

        List<Output> mappedResult = aggregationResults.getMappedResults();
        List<String> cate = mappedResult.stream().map(val -> val.category).collect(Collectors.toList());

        //custom query
        Optional<Expense> expenseCategory = expenseRepository.findEntry(null,"test");
        if (expenseCategory.isPresent()){
            System.out.println("FindEntry---->" + expenseCategory.get().getExpenseName());
        }

        //get the count
        long count = mongoTemplate.estimatedCount("expense");
        return mongoTemplate.find(query, Expense.class);
    }
    public Expense updateExpenses(Expense expense) {
         Expense updatedExpense =  customExpenseRepository.updateExpense(expense);
        return  updatedExpense;

    }
    public Expense getExpenseByName(String name){
        return expenseRepository.findExpenseByExpenseName(name);
    }
    public void removeExpenseByName(String expenseName){
        expenseRepository.deleteByExpenseName(expenseName);
    }

    private class Output {
        int count;
        String category;
    }

}
