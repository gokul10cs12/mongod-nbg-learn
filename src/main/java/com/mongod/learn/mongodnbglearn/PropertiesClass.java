package com.mongod.learn.mongodnbglearn;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.mongod.learn.mongodnbglearn.PropertiesClass.PREFIX;

@Data
@Component
@ConfigurationProperties(prefix = PREFIX)
public class PropertiesClass {
    public static final String PREFIX = "file.upload";

    private String location;

    private final String name = "Gokul";

    private final String comment = "test comment";
}
