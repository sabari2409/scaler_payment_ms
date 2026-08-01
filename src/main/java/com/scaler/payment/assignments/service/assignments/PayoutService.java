package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.clients.RazorpayPaymentGatewayClient;
import com.scaler.payment.assignments.dto.razorpay.PayoutPurpose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayoutService implements IPayoutService {

    @Autowired
    private RazorpayPaymentGatewayClient razorpayPaymentGatewayClient;

    @Override
    public String createPayoutToBankAccount(String accountNumber, Double amount, PayoutPurpose purpose, String referenceId, String narration) {
        return razorpayPaymentGatewayClient.createPayoutToBankAccount(accountNumber, amount, purpose, referenceId, narration);
    }
}

