package com.scaler.payment.assignments.controller.razorpay;

import com.razorpay.RazorpayException;
import com.scaler.payment.assignments.dto.razorpay.InitiatePaymentRequestDto;
import com.scaler.payment.assignments.dto.stripe.InitializePaymentRequestDto;
import com.scaler.payment.assignments.service.assignments.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping
    public String initiate() throws RazorpayException {
        String paymentLink = this.paymentService.doStandardPaymentLink();
        return paymentLink;
    }

    @PostMapping("initiatePayment")
    public String initiatePayment(@RequestBody InitiatePaymentRequestDto requestDto) throws RazorpayException {
        return this.paymentService.initiatePayment(
                requestDto.getName(), requestDto.getPhoneNumber(), requestDto.getEmail(), requestDto.getAmount(), requestDto.getDescription()
        );
    }

    @PostMapping("stripePaymentA1")
    public String getPaymentLink(@RequestBody InitializePaymentRequestDto requestDto) {
        return this.paymentService.getPaymentLink(
                requestDto.getAmount(), requestDto.getQuantity(), requestDto.getCallbackUrl(), requestDto.getProductName()
        );
    }
}
