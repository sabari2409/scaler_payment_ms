package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.dto.stripe.razorpay.CreateSubscriptionRequest;
import com.scaler.payment.assignments.dto.razorpay.RazorpayCustomerContactDetails;
import com.scaler.payment.assignments.dto.razorpay.RazorpayPlanRequest;
import com.scaler.payment.assignments.dto.razorpay.RazorpaySubscriptionRequest;
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