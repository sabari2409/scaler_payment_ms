package com.scaler.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayoutRequestDto {
    String accountNumber;
    Double amount;
    PayoutPurpose purpose;
    String referenceId;
    String narration;
}