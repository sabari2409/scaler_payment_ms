package com.scaler.payment.service;

import com.scaler.payment.dto.CreateSubscriptionRequest;
import com.scaler.payment.dto.RazorpayCustomerContactDetails;
import com.scaler.payment.dto.RazorpayPlanRequest;
import com.scaler.payment.dto.RazorpaySubscriptionRequest;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionRequestConverter {
    public RazorpaySubscriptionRequest from(CreateSubscriptionRequest createSubscriptionRequest) {
        RazorpaySubscriptionRequest razorpaySubscriptionRequest = new RazorpaySubscriptionRequest();
        razorpaySubscriptionRequest.setExpiryTime(createSubscriptionRequest.getEnding());
        razorpaySubscriptionRequest.setQuantity(createSubscriptionRequest.getChargeCount());
        razorpaySubscriptionRequest.setTotalCount(createSubscriptionRequest.getTotalNumberOfBillingCycles());
        razorpaySubscriptionRequest.setStartTime(createSubscriptionRequest.getStarting());

        RazorpayCustomerContactDetails razorpayCustomerContactDetails = new RazorpayCustomerContactDetails();
        razorpayCustomerContactDetails.setEmail(createSubscriptionRequest.getEmail());
        razorpayCustomerContactDetails.setPhoneNumber(createSubscriptionRequest.getPhone());
        razorpaySubscriptionRequest.setRazorpayCustomerContactDetails(razorpayCustomerContactDetails);

        RazorpayPlanRequest razorpayPlanRequest = new RazorpayPlanRequest();
        razorpayPlanRequest.setAmount(createSubscriptionRequest.getPlanAmount());
        razorpayPlanRequest.setName(createSubscriptionRequest.getPlanTitle());
        razorpayPlanRequest.setDescription(createSubscriptionRequest.getPlanDescription());
        razorpayPlanRequest.setFrequency(createSubscriptionRequest.getPlanPeriod().name());

        razorpaySubscriptionRequest.setRazorpayPlanRequest(razorpayPlanRequest);
        return razorpaySubscriptionRequest;
    }
}