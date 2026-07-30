package com.scaler.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefundRequestDto {
    Double amount;
    String receipt;
    RefundSpeed refundSpeed;
}
