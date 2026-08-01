package com.scaler.payment.assignments.dto.stripe;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Webhook {
    private String id;
    private String secret;
    private List<String> events = new ArrayList<>();
    private String url;
    private WebhookStatus status;
}
