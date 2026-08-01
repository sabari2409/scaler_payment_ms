package com.scaler.payment.assignments.dto.razorpay;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefundRequestDto {
    Double amount;
    String receipt;
    RefundSpeed refundSpeed;
}
