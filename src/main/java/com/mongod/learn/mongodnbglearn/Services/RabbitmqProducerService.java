package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.config.RabbitMqConfig;
import com.mongod.learn.mongodnbglearn.model.RabbitModel;
import com.mongod.learn.mongodnbglearn.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqProducerService {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitmqProducerService.class);

    private RabbitTemplate rabbitTemplate;

    RabbitmqProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        LOG.info("message sent--> {}", message);
        RabbitModel model = new RabbitModel();
        model.setMessage("this is a model message");
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, RabbitMqConfig.ROUTING_KEY, message);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, RabbitMqConfig.ROUTING_KEY2, model);
    }

}
