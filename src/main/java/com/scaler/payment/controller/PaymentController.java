package com.scaler.payment.controller;

import com.razorpay.RazorpayException;
import com.scaler.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public String initiate() throws RazorpayException {
        String paymentLink = this.paymentService.doPayment();
        return paymentLink;
    }
}
