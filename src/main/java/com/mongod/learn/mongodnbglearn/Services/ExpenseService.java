package com.mongod.learn.mongodnbglearn.Services;

import com.google.common.collect.Lists;
import com.mongod.learn.mongodnbglearn.model.Expense;
import com.mongod.learn.mongodnbglearn.model.ExpenseCategory;
import com.mongod.learn.mongodnbglearn.model.FileIdentity;
import com.mongod.learn.mongodnbglearn.repository.CustomExpenseRepository;
import com.mongod.learn.mongodnbglearn.repository.ExpenseRepository;
import com.mongodb.client.model.ValidationAction;
import org.bson.Document;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.BulkOperationException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLOutput;
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



        Set<FileIdentity> duplicates = new HashSet<>();
        Set<String> idsToDelete = new HashSet<>();
        //bulk operation

        AggregationOptions aggregationOptions = Aggregation.newAggregationOptions()
                .cursorBatchSize(2)
                .build();


        //aggregation
        Aggregation aggregation2 = Aggregation.newAggregation(
                Aggregation.match(where( "fileIdentity.sha256").ne(null)),
                Aggregation.group( "fileIdentity.sha256").count().as("count"),
                Aggregation.match(where("count").gt(1)),
                Aggregation.project( "count").andExpression("_id").as( "sha256"))
                .withOptions(aggregationOptions);


        List<Output> mappedResult = new ArrayList<>();

        AggregationResults<Output> aggregationResults = mongoTemplate.aggregate(aggregation2,"expense", Output.class );


        mappedResult = aggregationResults.getMappedResults();


        Set<String> shaSet = mappedResult.stream().map(val -> val.sha256).collect(Collectors.toSet());
        Query query1 = new Query();
        Criteria criteria1 = where("fileIdentity.sha256").in(shaSet).and("fileIdentity.md5").exists(false);
        query1.addCriteria(criteria1);

        mongoTemplate.remove(query1, Expense.class);


//        Query newQuery = new Query();
        Criteria myCriteria =  where("fileIdentity.sha256").in(shaSet).and("fileIdentity.md5").exists(true);
        Query query2 = new Query();

        query2.addCriteria(myCriteria);
        List<Expense> queryResult = mongoTemplate.find(query2, Expense.class);
        queryResult.forEach(entity ->{
            if(!duplicates.add(entity.getFileIdentity())) {
                System.out.println("duplicate ->" + entity.getFileIdentity().getSha256());
                shaSet.add(entity.getFileIdentity().getSha256());
                idsToDelete.add(entity.getId());
            }
        });

        if (!idsToDelete.isEmpty()){
            Query query3 = new Query();
            Criteria newCriteria = where("_id").in(idsToDelete).and("fileIdentity.md5").exists(true);
            query3.addCriteria(newCriteria);

            mongoTemplate.remove(query3, Expense.class);
        }

        int i =0;

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
        String sha256;
    }

}
