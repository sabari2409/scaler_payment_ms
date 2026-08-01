package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.clients.StripePaymentGateway;
import com.scaler.payment.assignments.config.RazorpayConfig;

import com.razorpay.PaymentLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private RazorpayConfig razorpayConfig;

    @Autowired
    private StripePaymentGateway stripePaymentGateway;


    public String doStandardPaymentLink() throws RazorpayException {

        // Standard payment link which has upi, netbanking etc
        RazorpayClient razorpay = new RazorpayClient("<api_key>", "<secret_key>");
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", 1000);
        paymentLinkRequest.put("currency", "INR");
        paymentLinkRequest.put("accept_partial", true);
        paymentLinkRequest.put("first_min_partial_amount", 100);
        paymentLinkRequest.put("expire_by", 1785196800);
        paymentLinkRequest.put("reference_id", "TS198987"); // This reference id should be unique
        paymentLinkRequest.put("description", "Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put("name", "+919790134498");
        customer.put("contact", "Sabari Rajiv"); // It should be 8 to 14 characters max
        customer.put("email", "pepdistech@gmail.com");
        paymentLinkRequest.put("customer", customer);
        JSONObject notify = new JSONObject();
        notify.put("sms", true);
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("reminder_enable", true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name", "Life Insurance Policy");
        paymentLinkRequest.put("notes", notes);
        paymentLinkRequest.put("callback_url", "http://www.scaler.com");
        paymentLinkRequest.put("callback_method", "get");


//        UPI Payment link no supported in test api's
//        JSONObject paymentLinkRequest = new JSONObject();
//        paymentLinkRequest.put("upi_link", true);
//        paymentLinkRequest.put("amount", 1000);
//        paymentLinkRequest.put("currency", "<currency>");
//        paymentLinkRequest.put("accept_partial", false);
//        paymentLinkRequest.put("first_min_partial_amount", 100);
//        paymentLinkRequest.put("description", "Payment for policy no #23456");
//        JSONObject customer = new JSONObject();
//        customer.put("name", "<name>");
//        customer.put("contact", "<phone>");
//        customer.put("email", "<email>");
//        paymentLinkRequest.put("customer", customer);
//        JSONObject notify = new JSONObject();
//        notify.put("sms", true);
//        notify.put("email", true);
//        paymentLinkRequest.put("notify", notify);
//        paymentLinkRequest.put("reminder_enable", true);
//        JSONObject notes = new JSONObject();
//        notes.put("policy_name", "Life Insurance Policy");
//        paymentLinkRequest.put("notes", notes);

        PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
        return payment.get("short_url");
    }

    @Override
    public String initiatePayment(String name, String phoneNumber, String email, Double amount,
                                  String description) throws RazorpayException {
        //        UPI Payment link no supported in test api's
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("upi_link", true);
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency", "INR");
        paymentLinkRequest.put("accept_partial", false);
        paymentLinkRequest.put("first_min_partial_amount", 100);
        paymentLinkRequest.put("description", description);
        JSONObject customer = new JSONObject();
        customer.put("name", name);
        customer.put("contact", phoneNumber);
        customer.put("email", email);
        paymentLinkRequest.put("customer", customer);
        JSONObject notify = new JSONObject();
        notify.put("sms", true);
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("reminder_enable", true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name", "Life Insurance Policy");
        paymentLinkRequest.put("notes", notes);
        RazorpayClient razorpayClient = razorpayConfig.getRazorpayClient();
        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        return payment.get("short_url");
    }

    public String getPaymentLink(Long amount, Long quantity, String callbackUrl, String productName) {
        return this.stripePaymentGateway.getPaymentLink(amount, quantity, callbackUrl, productName);
    }
}
