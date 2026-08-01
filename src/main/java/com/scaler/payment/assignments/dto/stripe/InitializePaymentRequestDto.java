package com.scaler.payment.assignments.dto.stripe;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitializePaymentRequestDto {
    Long amount;
    Long quantity;
    String callbackUrl;
    String productName;
}
