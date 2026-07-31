package com.scaler.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RazorpayPlanRequest {
    String frequency;
    String name;
    String description;
    Double amount;
}
