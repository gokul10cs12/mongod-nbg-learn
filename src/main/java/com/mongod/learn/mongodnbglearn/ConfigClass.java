package com.mongod.learn.mongodnbglearn;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class ConfigClass {

    @Bean
    MyTestClass myTestClass(PropertiesClass myProperties){
        return new MyTestClass(myProperties.getName(), myProperties.getComment());
    }


}
