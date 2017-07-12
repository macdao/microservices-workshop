package com.example.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ConsumerApplication {

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyBean myBean;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class);
    }


    @RequestMapping("/c")
    @PreAuthorize("hasRole('USER')")
    public String home(Principal principal) {
        final String response = restTemplate.getForObject("http://provider/p", String.class);
        return "Consumer:Response/" + response + ":config/" + myBean.getName() + ":principal/" + principal.getName();
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
