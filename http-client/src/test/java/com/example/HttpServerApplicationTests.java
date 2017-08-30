package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureStubRunner(ids = {"com.example:http-server:+:stubs:8080"}, workOffline = true)
public class HttpServerApplicationTests {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void shouldBeRejectedDueToAbnormalLoanAmount() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/vnd.fraud.v1+json");
        String response = restTemplate.exchange("http://localhost:8080/fraudcheck", HttpMethod.PUT,
                new HttpEntity<>("{\"clientId\":\"1234567890\",\"loanAmount\":99999}", httpHeaders),
                String.class).getBody();
        assertThat(response).isEqualTo("{\"fraudCheckStatus\":\"FRAUD\",\"rejectionReason\":\"Amount too high\"}");
    }

}
