package com.mongod.learn.mongodnbglearn.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String QUEUE_NAME = "spring-queue";
    public static final String SECOND_QUEUE = "second-queue";

    public static final String EXCHANGE_NAME ="spring-rabbit-exchange";

    public static final String ROUTING_KEY = "routing.key";

    public static final String ROUTING_KEY2 = "routing.key2";


    //spring bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return  new Queue(QUEUE_NAME);
    }

    //spring bean for rabbitmq queue
    @Bean
    public Queue queueTwo(){
        return  new Queue(SECOND_QUEUE);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange() {
        return   new TopicExchange(EXCHANGE_NAME);
    }

    //binding between queue and exchange using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with("routing.key*");
    }

    @Bean
    public Binding newBinding(){
        return BindingBuilder
                .bind(queueTwo())
                .to(exchange())
                .with("routing.*");
    }

}
