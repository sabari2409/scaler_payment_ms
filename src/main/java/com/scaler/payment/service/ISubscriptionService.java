package com.scaler.payment.service;

import com.scaler.payment.dto.CreateSubscriptionRequest;

public interface ISubscriptionService {
    String createSubscription(CreateSubscriptionRequest subscriptionRequest);
}

