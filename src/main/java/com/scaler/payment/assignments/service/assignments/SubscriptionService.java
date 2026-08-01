package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.clients.RazorpayPaymentGatewayClient;
import com.scaler.payment.assignments.clients.StripePaymentGateway;
import com.scaler.payment.assignments.dto.razorpay.CreateSubscriptionRequest;
import com.stripe.param.PlanCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService implements ISubscriptionService {

    @Autowired
    private RazorpayPaymentGatewayClient razorpayPaymentGatewayClient;

    @Autowired
    private SubscriptionRequestConverter requestConverter;

    @Autowired
    private StripePaymentGateway stripePaymentGateway;

    @Override
    public String createSubscription(CreateSubscriptionRequest subscriptionRequest) {
//        RazorpaySubscriptionRequest razorpaySubscriptionRequest = this.requestConverter.from(subscriptionRequest);
//        Subscription subscription = this.razorpayPaymentGatewayClient.createSubscriptionLink(razorpaySubscriptionRequest);
//        return subscription.get("short_url");
        return null;
    }

    @Override
    public String createStripeSubscriptionForProduct(String customerName, String customerEmail, Long productAmount, String productName, PlanCreateParams.Interval interval) {
        return this.stripePaymentGateway.createSubscriptionForProduct(
                customerName, customerEmail, productAmount, productName, interval
        );
    }
}
