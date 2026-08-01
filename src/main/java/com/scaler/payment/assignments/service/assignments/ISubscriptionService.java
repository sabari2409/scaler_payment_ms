package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.dto.stripe.razorpay.CreateSubscriptionRequest;

public interface ISubscriptionService {
    String createSubscription(CreateSubscriptionRequest subscriptionRequest);
}

