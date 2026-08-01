package com.scaler.payment.assignments.service.assignments;

import com.razorpay.RazorpayException;

public interface IPaymentService {
    String doStandardPaymentLink() throws RazorpayException;

    String initiatePayment(String name, String phoneNumber, String email, Double amount, String description) throws RazorpayException;

    String getPaymentLink(Long amount, Long quantity, String callbackUrl, String productName);


}
