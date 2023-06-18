package com.mongod.learn.mongodnbglearn.controller;

import com.mongod.learn.mongodnbglearn.Services.RabbitmqProducerService;
import com.mongod.learn.mongodnbglearn.model.RabbitModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/rabbit")
@RestController
public class RabbitMqController {

    private final RabbitmqProducerService rabbitService;

    public RabbitMqController(RabbitmqProducerService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @PostMapping("/message")
    void postRabbitMessage(@RequestBody RabbitModel message){
        rabbitService.sendMessage(message.getMessage());
    }
}
