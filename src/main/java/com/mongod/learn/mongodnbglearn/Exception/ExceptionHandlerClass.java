package com.mongod.learn.mongodnbglearn.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerClass {

//    @ExceptionHandler(value = ExpenseNotFound.class)
//    public ResponseEntity<Object> exception(ExpenseNotFound exception){
//        return new ResponseEntity<>("Product Not found", HttpStatus.NOT_FOUND);
//    }

}
