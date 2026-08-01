package com.scaler.payment.assignments.controller.razorpay;

import com.scaler.payment.assignments.dto.razorpay.PayoutRequestDto;
import com.scaler.payment.assignments.service.assignments.IPayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payout")
public class PayoutController {

    @Autowired
    private IPayoutService payoutService;

    @PostMapping
    public String createPayout(@RequestBody PayoutRequestDto requestDto) {
        return this.payoutService.createPayoutToBankAccount(
                requestDto.getAccountNumber(), requestDto.getAmount(),
                requestDto.getPurpose(), requestDto.getReferenceId(), requestDto.getNarration()
        );
    }
}
