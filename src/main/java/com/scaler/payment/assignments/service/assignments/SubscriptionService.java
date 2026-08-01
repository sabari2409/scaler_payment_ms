package com.scaler.payment.assignments.service.assignments;

import com.razorpay.Subscription;
import com.scaler.payment.assignments.clients.RazorpayPaymentGatewayClient;
import com.scaler.payment.assignments.dto.stripe.razorpay.CreateSubscriptionRequest;
import com.scaler.payment.assignments.dto.razorpay.RazorpaySubscriptionRequest;
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
