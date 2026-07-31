package com.scaler.payment.controller;

import com.scaler.payment.dto.PayoutRequestDto;
import com.scaler.payment.service.IPayoutService;
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
