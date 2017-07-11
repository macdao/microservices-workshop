package com.example.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class HelloController {
    @Autowired
    ApplicationContext context;

    @Autowired
    private MyBean myBean;

    @RequestMapping(path = "/hello")
    public String hello() {
        return "hello:" + myBean.getName() + ":" + Arrays.toString(context.getEnvironment().getActiveProfiles());
    }
}
