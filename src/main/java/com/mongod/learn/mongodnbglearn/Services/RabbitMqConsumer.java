package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMqConsumer.class);

    @RabbitListener(queues = {RabbitMqConfig.QUEUE_NAME})
    void consume(String message) {
        LOG.info("Received message : --->{}", message);
    }
}
