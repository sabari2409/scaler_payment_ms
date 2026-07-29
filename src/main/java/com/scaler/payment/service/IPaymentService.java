package com.scaler.payment.service;

import com.razorpay.RazorpayException;

public interface IPaymentService {
    String doStandardPaymentLink() throws RazorpayException;

    String initiatePayment(String name, String phoneNumber, String email, Double amount, String description) throws RazorpayException;
}
