package com.scaler.payment.assignments.dto.razorpay;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RazorpaySubscriptionRequest {
    Long quantity;
    Long totalCount;
    Long startTime;
    Long expiryTime;
    RazorpayCustomerContactDetails razorpayCustomerContactDetails;
    RazorpayPlanRequest razorpayPlanRequest;
}

