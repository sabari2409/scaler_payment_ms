package com.scaler.payment.service;

import com.scaler.payment.dto.PayoutPurpose;

public interface IPayoutService {
    String createPayoutToBankAccount(String accountNumber, Double amount, PayoutPurpose purpose, String referenceId, String narration);
}