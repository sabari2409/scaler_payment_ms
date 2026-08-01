package com.scaler.payment.assignments.clients;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import com.scaler.payment.assignments.config.RazorpayConfig;
import com.scaler.payment.assignments.dto.razorpay.PayoutPurpose;
import com.scaler.payment.assignments.dto.razorpay.RazorpayCustomerContactDetails;
import com.scaler.payment.assignments.dto.razorpay.RazorpaySubscriptionRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import com.razorpay.Subscription;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Component
public class RazorpayPaymentGatewayClient {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private RazorpayConfig razorpayConfig;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    private final String offerId = "offer_JTUADI4ZWBGWur";

    private final String paymentId = "pay_39QqoUAi66xm2f"; //use this paymentId, wherever needed

    private final String path = "https://api.razorpay.com/v1/payouts";

    // Assignment 3
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

    // Assignment 3
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


    // Assignment 4
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


    // Assignment 5
    public String createPayoutToBankAccount(String accountNumber, Double amount, PayoutPurpose purpose, String referenceId, String narration) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("", "");
        headers.set("X-Payout-Idempotency", "53cda91c-8f81-4e77-bbb9-7388f4ac6bf4");

        JSONObject payoutRequest = new JSONObject();
        payoutRequest.put("account_number", accountNumber);
        payoutRequest.put("fund_account_id", "fa_00000000000001");
        payoutRequest.put("amount", amount);
        payoutRequest.put("currency", "INR");
        payoutRequest.put("mode", "IMPS");
        payoutRequest.put("purpose", purpose.toString());
        payoutRequest.put("queue_if_low_balance", true);
        payoutRequest.put("reference_id", referenceId);
        payoutRequest.put("narration", narration);

        JSONObject notes = new JSONObject();
        notes.put("notes_key_1", "Tea, Earl Grey, Hot");
        notes.put("notes_key_2", "Tea, Earl Grey… decaf.");
        payoutRequest.put("notes", notes);

        HttpEntity<String> httpEntity = new HttpEntity<>(payoutRequest.toString(), headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(path, HttpMethod.POST, httpEntity, String.class);
        return responseEntity.getBody();

    }
}