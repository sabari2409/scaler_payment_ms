package com.scaler.payment.assignments.dto.stripe;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SessionDto {
    String id;
    Long total;
    Long expiry;
    String url;
}

