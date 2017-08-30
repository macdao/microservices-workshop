package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FraudDetectionController {

    @RequestMapping(path = "/fraudcheck", method = RequestMethod.PUT, produces = "application/vnd.fraud.v1+json", consumes = "application/vnd.fraud.v1+json")
    public Map<String, String> hello() {
        return null;
//        final HashMap<String, String> result = new HashMap<>();
//        result.put("fraudCheckStatus", "FRAUD");
//        result.put("rejectionReason", "Amount too high");
//        return result;
    }
}
