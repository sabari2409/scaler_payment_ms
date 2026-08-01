package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.dto.razorpay.CreateSubscriptionRequest;
import com.stripe.param.PlanCreateParams;

public interface ISubscriptionService {
    String createSubscription(CreateSubscriptionRequest subscriptionRequest);

    String createStripeSubscriptionForProduct(String customerName,String customerEmail,Long productAmount, String productName, PlanCreateParams.Interval interval);


}

