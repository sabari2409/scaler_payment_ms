package com.scaler.payment.assignments.controller.razorpay;

import com.scaler.payment.assignments.dto.stripe.razorpay.CreateSubscriptionRequest;
import com.scaler.payment.assignments.service.assignments.ISubscriptionService;
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
