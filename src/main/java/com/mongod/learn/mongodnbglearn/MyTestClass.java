package com.mongod.learn.mongodnbglearn;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyTestClass {
    private String name;
    private String comment;

    public void printValues(){
        System.out.println("\n name:" + name + "\n" + "comment:" + comment);
    }

}
