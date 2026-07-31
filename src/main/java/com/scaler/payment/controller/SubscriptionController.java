package com.scaler.payment.controller;

import com.scaler.payment.dto.CreateSubscriptionRequest;
import com.scaler.payment.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private ISubscriptionService subscriptionService;

    @PostMapping
    public String createSubscription(@RequestBody CreateSubscriptionRequest request) {
        return this.subscriptionService.createSubscription(request);
    }
}
