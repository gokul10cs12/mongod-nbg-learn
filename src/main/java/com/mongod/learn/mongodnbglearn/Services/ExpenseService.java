package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.MyTestClass;
import com.mongod.learn.mongodnbglearn.model.CustomEventModel;
import com.mongod.learn.mongodnbglearn.model.Expense;
import com.mongod.learn.mongodnbglearn.model.ExpenseCategory;
import com.mongod.learn.mongodnbglearn.model.FileIdentity;
import com.mongod.learn.mongodnbglearn.repository.CustomExpenseRepository;
import com.mongod.learn.mongodnbglearn.repository.ExpenseRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final CustomExpenseRepository customExpenseRepository;

    private final MongoTemplate mongoTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final MyTestClass myTestClass;

    @EventListener(ApplicationReadyEvent.class)
    public void startup(){
        System.out.println("startup Gokul----->");
    }


    public ExpenseService(ExpenseRepository expenseRepository, CustomExpenseRepository customExpenseRepository, MongoTemplate mongoTemplate, ApplicationEventPublisher applicationEventPublisher, MyTestClass myTestClass) {
        this.expenseRepository = expenseRepository;
        this.customExpenseRepository = customExpenseRepository;
        this.mongoTemplate = mongoTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
        this.myTestClass = myTestClass;
    }

    public Expense addExpense(Expense expense){
        expense.setId(UUID.randomUUID().toString());
        return expenseRepository.insert(expense);
    }
    public void removeExpense(){}
    public List<Expense> getAllExpenses(){
        ExpenseCategory []categories = new ExpenseCategory[]{ExpenseCategory.ENTERTAINMENT, ExpenseCategory.UTILITIES};
        CustomEventModel customEventModel = new CustomEventModel();
        customEventModel.setMessage("sent message from expense service");
        applicationEventPublisher.publishEvent(customEventModel);
        Set<String> ids = new HashSet<>();
        Criteria criteria = where("category").in(categories);
        Query query = new Query();
        query.addCriteria(criteria);

        myTestClass.printValues();

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


        List<Output> mappedResult;

        AggregationResults<Output> aggregationResults = mongoTemplate.aggregate(aggregation2,"expense", Output.class );

        mappedResult = aggregationResults.getMappedResults();
        Set<String> shaSet = mappedResult.stream().map(val -> val.sha256).collect(Collectors.toSet());
        Criteria criteria1 = where("fileIdentity.sha256").in(shaSet).and("fileIdentity.md5").exists(false);
        removeRecords(criteria1);

        Criteria myCriteria =  where("fileIdentity.sha256").in(shaSet).and("fileIdentity.md5").exists(true);
        Query query2 = new Query();
        query2.addCriteria(myCriteria);
        List<Expense> queryResult = mongoTemplate.find(query2, Expense.class);
        Set<FileIdentity> duplicates = new HashSet<>();
        queryResult.forEach(entity ->{
            if(!duplicates.add(entity.getFileIdentity())) {
                idsToDelete.add(entity.getId());
            }
        });

        Criteria newCriteria = where("_id").in(idsToDelete);
        removeRecords(newCriteria);
        return expenseRepository.findAll();
    }

    private void removeRecords(Criteria criteria){
        Query query = new Query();
        query.addCriteria(criteria);
        mongoTemplate.remove(query, Expense.class);
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
