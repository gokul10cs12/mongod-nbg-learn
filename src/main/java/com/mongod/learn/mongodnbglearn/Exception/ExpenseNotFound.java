package com.mongod.learn.mongodnbglearn.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ExpenseNotFound extends RuntimeException{
//    public ExpenseNotFound(String message){
//        super(message);
//    }
}
