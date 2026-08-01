package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.dto.stripe.Webhook;

import java.util.List;


public interface IWebhookService {
    Webhook createWebhook(String url, List<String> events);

    Boolean deleteWebhook(String webhookId);

    Webhook updateWebhook(String updatedUrl, List<String> events, String webhookId);
}
