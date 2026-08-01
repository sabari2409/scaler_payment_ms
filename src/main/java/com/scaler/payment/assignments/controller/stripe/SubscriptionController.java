package com.scaler.payment.assignments.controller.stripe;

import com.scaler.payment.assignments.dto.stripe.SubscriptionRequestDto;
import com.scaler.payment.assignments.service.assignments.ISubscriptionService;
import com.stripe.param.PlanCreateParams;
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
    public String createSubscriptionForProduct(@RequestBody SubscriptionRequestDto requestDto) {
        return this.subscriptionService.createStripeSubscriptionForProduct(
                requestDto.getCustomerName(),
                requestDto.getCustomerEmail(),
                requestDto.getProductAmount(),
                requestDto.getProductName(),
                PlanCreateParams.Interval.MONTH
        );
    }
}
