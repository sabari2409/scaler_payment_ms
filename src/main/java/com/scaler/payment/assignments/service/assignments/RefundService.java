package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.clients.RazorpayPaymentGatewayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundService implements IRefundService {

    @Autowired
    private RazorpayPaymentGatewayClient razorpayPaymentGatewayClient;

    public String issueRefund(Double amount, String receipt) {
        return this.razorpayPaymentGatewayClient.issueInstantRefund(amount, receipt);
    }

    public String updateRefund(String refundId, JSONObject jsonObject) {
        return this.razorpayPaymentGatewayClient.updateRefund(refundId, jsonObject);
    }
}