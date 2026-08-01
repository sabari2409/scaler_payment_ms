package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.dto.razorpay.PayoutPurpose;

public interface IPayoutService {
    String createPayoutToBankAccount(String accountNumber, Double amount, PayoutPurpose purpose, String referenceId, String narration);
}