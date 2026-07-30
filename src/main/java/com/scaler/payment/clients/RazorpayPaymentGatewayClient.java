package com.scaler.payment.clients;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import com.scaler.payment.config.RazorpayConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RazorpayPaymentGatewayClient {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private RazorpayConfig razorpayConfig;

    private final String paymentId = "pay_39QqoUAi66xm2f"; //use this paymentId, wherever needed

    public String issueInstantRefund(Double amount, String receipt) {

        try {
            JSONObject refundRequest = new JSONObject();
            refundRequest.put("amount", amount);
            refundRequest.put("speed", "optimum");
            refundRequest.put("receipt", receipt);
            JSONObject notes = new JSONObject();
            notes.put("notes_key_1", "Tea, Earl Grey, Hot");
            notes.put("notes_key_2", "Tea, Earl Grey… decaf.");
            refundRequest.put("notes", notes);
            Refund refund = razorpayConfig.getRazorpayClient().payments.refund(paymentId, refundRequest);
            return refund.get("id");
        } catch (RazorpayException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public String updateRefund(String refundId, JSONObject jsonObject) {
        try {

            JSONObject refundRequest = new JSONObject();
            refundRequest.put("notes", jsonObject);
            Refund refund = razorpayConfig.getRazorpayClient().refunds.edit(refundId, refundRequest);
            return refund.get("id");
        } catch (RazorpayException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}