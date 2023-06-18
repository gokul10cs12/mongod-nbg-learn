package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.config.RabbitMqConfig;
import com.mongod.learn.mongodnbglearn.model.RabbitModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSecondConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMqSecondConsumer.class);

    @RabbitListener(queues = {RabbitMqConfig.SECOND_QUEUE})
    void getMessageFromRabbit(RabbitModel model) {
        LOG.info("receiver by second consumer---> {}", model.getMessage());
    }
}
//raf : https://www.springcloud.io/post/2022-03/messaging-using-rabbitmq-in-spring-boot-application/#gsc.tab=0
//ref : types of exchanges implementation https://www.javainuse.com/messaging/rabbitmq/exchange