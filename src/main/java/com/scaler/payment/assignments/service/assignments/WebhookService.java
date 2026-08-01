package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.clients.StripePaymentGateway;
import com.scaler.payment.assignments.dto.stripe.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WebhookService implements IWebhookService {

    @Autowired
    private StripePaymentGateway stripePaymentGateway;

    public Webhook createWebhook(String url, List<String> events) {
        return this.stripePaymentGateway.createWebhook(url, events);
    }

    public Boolean deleteWebhook(String webhookId) {
        return this.stripePaymentGateway.deleteWebhook(webhookId);
    }

    public Webhook updateWebhook(String updatedUrl, List<String> events, String webhookId) {
        return this.stripePaymentGateway.updateWebhook(updatedUrl, events, webhookId);
    }
}