package com.scaler.payment.assignments.controller.stripe;

import com.scaler.payment.assignments.dto.stripe.Webhook;
import com.scaler.payment.assignments.dto.stripe.WebhookDto;
import com.scaler.payment.assignments.service.assignments.IWebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private IWebhookService webhookService;

    @PostMapping
    public Webhook create(@RequestBody WebhookDto request) {
        return this.webhookService.createWebhook(request.getUrl(), request.getEvents());
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable String id) {
        return this.webhookService.deleteWebhook(id);
    }

    @PatchMapping("{id}")
    public Webhook update(@RequestBody WebhookDto req, @PathVariable("id") String webhookId) {
        return this.webhookService.updateWebhook(
                req.getUrl(), req.getEvents(), webhookId
        );
    }
}