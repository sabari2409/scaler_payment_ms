package com.scaler.payment.clients;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import com.scaler.payment.config.RazorpayConfig;
import com.scaler.payment.dto.RazorpayCustomerContactDetails;
import com.scaler.payment.dto.RazorpayPlanRequest;
import com.scaler.payment.dto.RazorpaySubscriptionRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.razorpay.Subscription;

import java.util.ArrayList;
import java.util.List;


@Component
public class RazorpayPaymentGatewayClient {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private RazorpayConfig razorpayConfig;

    private final String offerId = "offer_JTUADI4ZWBGWur";

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


    public Subscription createSubscriptionLink(RazorpaySubscriptionRequest subscriptionInput) {
        try {

            JSONObject subscriptionRequest = new JSONObject();
            subscriptionRequest.put("plan_id", "plan_HoYg68p5kmuvzD");
            subscriptionRequest.put("total_count", subscriptionInput.getTotalCount());
            subscriptionRequest.put("quantity", subscriptionInput.getQuantity());
            subscriptionRequest.put("customer_notify", true);
            subscriptionRequest.put("start_at", subscriptionInput.getStartTime());
            subscriptionRequest.put("expire_by", subscriptionInput.getExpiryTime());
            List<Object> addons = new ArrayList<>();
            JSONObject linesItem = new JSONObject();
            JSONObject item = new JSONObject();
            item.put("name", subscriptionInput.getRazorpayPlanRequest().getName());
            item.put("amount", subscriptionInput.getRazorpayPlanRequest().getAmount());
            item.put("currency", "INR");
            linesItem.put("item", item);
            addons.add(linesItem);
            subscriptionRequest.put("addons", addons);
            subscriptionRequest.put("offer_id", offerId);
            JSONObject notes = new JSONObject();
            notes.put("notes_key_1", "Tea, Earl Grey, Hot");
            notes.put("notes_key_2", "Tea, Earl Grey… decaf.");
            subscriptionRequest.put("notes", notes);
            JSONObject notifyInfo = new JSONObject();

            RazorpayCustomerContactDetails customerContactDetails = subscriptionInput.getRazorpayCustomerContactDetails();

            if (customerContactDetails != null) {
                notifyInfo.put("notify_phone", subscriptionInput.getRazorpayCustomerContactDetails().getPhoneNumber());
                notifyInfo.put("notify_email", subscriptionInput.getRazorpayCustomerContactDetails().getEmail());
                subscriptionRequest.put("notify_info", notifyInfo);
            } else {
                throw new RazorpayException("Failed to create plan");
            }

            Subscription subscription = razorpayConfig.getRazorpayClient().subscriptions.create(subscriptionRequest);
            return subscription;
        } catch (RazorpayException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}