package com.scaler.payment.service;

import com.razorpay.Subscription;
import com.scaler.payment.clients.RazorpayPaymentGatewayClient;
import com.scaler.payment.dto.CreateSubscriptionRequest;
import com.scaler.payment.dto.RazorpaySubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService implements ISubscriptionService {

    @Autowired
    private RazorpayPaymentGatewayClient razorpayPaymentGatewayClient;

    @Autowired
    private SubscriptionRequestConverter requestConverter;

    @Override
    public String createSubscription(CreateSubscriptionRequest subscriptionRequest) {
        RazorpaySubscriptionRequest razorpaySubscriptionRequest = this.requestConverter.from(subscriptionRequest);
        Subscription subscription = this.razorpayPaymentGatewayClient.createSubscriptionLink(razorpaySubscriptionRequest);
        return subscription.get("short_url");
    }
}
