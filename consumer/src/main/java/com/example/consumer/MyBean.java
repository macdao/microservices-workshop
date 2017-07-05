package com.example.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class MyBean {

    @Value("${my.name}")
    private String name;

    public String getName() {
        return name;
    }
}
