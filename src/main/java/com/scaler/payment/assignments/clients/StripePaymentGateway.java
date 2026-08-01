package com.scaler.payment.assignments.clients;

import com.scaler.payment.assignments.dto.stripe.SessionDto;
import com.scaler.payment.assignments.dto.stripe.Webhook;
import com.scaler.payment.assignments.dto.stripe.WebhookStatus;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StripePaymentGateway {

    @Value("${stripe.key}")
    public String apiKey;

    private final Long trialDays = 730L;

    // assignment 1
    public String getPaymentLink(Long amount, Long quantity, String callbackUrl, String productName) {

        try {
            String priceId = this.createPrice(amount);
            StripeClient client = new StripeClient(this.apiKey);
            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(priceId)
                                            .setQuantity(quantity)
                                            .build()
                            )
                            .build();

            PaymentLink paymentLink = client.v1().paymentLinks().create(params);
            return paymentLink.getUrl();
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    // Assignment 2
    public SessionDto createSession(String successUrl, List<Long> amounts,
                                    List<String> productNames, List<Long> quantities) {
        try {
            StripeClient client = new StripeClient(this.apiKey);
            String priceId = this.getCheckoutPriceId(amounts);
            long total = 0;
            for (int i = 0; i < amounts.size(); i++) {
                total += amounts.get(i) * quantities.get(i);
            }
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setSuccessUrl(successUrl)
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPrice(priceId)
                                            .setQuantity(2L)
                                            .build()
                            )
                            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                            .build();

            Session session = client.v1().checkout().sessions().create(params);
            SessionDto sessionDto = new SessionDto();
            sessionDto.setId(session.getId());
            sessionDto.setUrl(session.getUrl());
            sessionDto.setTotal(total);
            sessionDto.setExpiry(session.getExpiresAt());
            return sessionDto;
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    // Assignment 3
    public String createSubscriptionForProduct(String customerName, String customerEmail, Long productAmount, String productName, PlanCreateParams.Interval interval) {
        try {
            StripeClient client = new StripeClient(this.apiKey);
            String priceId = this.createPrice(productAmount);
            Customer customerDetails = this.createCustomer(customerName, customerEmail);
            SubscriptionCreateParams params =
                    SubscriptionCreateParams.builder()
                            .setCustomer(customerDetails.getId())
                            .setTrialPeriodDays(trialDays)
                            .addItem(
                                    SubscriptionCreateParams.Item.builder()
                                            .setPrice(priceId)
                                            .build()
                            )
                            .build();

            Subscription subscription = client.v1().subscriptions().create(params);
            return subscription.getId();
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    // Assignment 3 private method
    private Customer createCustomer(String customerName, String customerEmail) {
        try {
            StripeClient client = new StripeClient(this.apiKey);
            CustomerCreateParams params =
                    CustomerCreateParams.builder()
                            .setName(customerName)
                            .setEmail(customerEmail)
                            .build();
            Customer customer = client.v1().customers().create(params);
            return customer;
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


    // Assignment 4
    public Webhook createWebhook(String url, List<String> events) {
        try {
            StripeClient client = new StripeClient(this.apiKey);
            WebhookEndpointCreateParams params = WebhookEndpointCreateParams.builder()
                    .addEnabledEvent(WebhookEndpointCreateParams.EnabledEvent.PAYMENT_LINK__CREATED)
                    .setUrl(url)
                    .build();
            WebhookEndpoint webhookEndpoint = client.webhookEndpoints().create(params);

            Webhook webhook = new Webhook();
            webhook.setSecret(webhookEndpoint.getSecret());
            webhook.setId(webhookEndpoint.getId());
            webhook.setUrl(webhookEndpoint.getUrl());
            webhook.setStatus(WebhookStatus.valueOf(webhookEndpoint.getStatus()));
            webhook.setEvents(webhookEndpoint.getEnabledEvents());
            return webhook;
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Boolean deleteWebhook(String webhookId) {
        try {
            StripeClient client = new StripeClient(this.apiKey);
            WebhookEndpoint webhookEndpoint =
                    client.webhookEndpoints().delete(webhookId);
            return webhookEndpoint.getDeleted();
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Webhook updateWebhook(String updatedUrl, List<String> events, String webhookId) {
        try {
            StripeClient client = new StripeClient(this.apiKey);
            WebhookEndpointUpdateParams params = WebhookEndpointUpdateParams.builder()
                    .addEnabledEvent(WebhookEndpointUpdateParams.EnabledEvent.PAYMENT_LINK__CREATED)
                    .addEnabledEvent(WebhookEndpointUpdateParams.EnabledEvent.PAYMENT_LINK__UPDATED)
                    .setUrl(updatedUrl)
                    .build();
            WebhookEndpoint webhookEndpoint = client.webhookEndpoints().update(webhookId, params);

            Webhook webhook = new Webhook();
            webhook.setId(webhookEndpoint.getId());
            webhook.setUrl(webhookEndpoint.getUrl());
            webhook.setStatus(WebhookStatus.valueOf(webhookEndpoint.getStatus()));
            webhook.setEvents(webhookEndpoint.getEnabledEvents());
            return webhook;
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


    // Assignment 2
    private String getCheckoutPriceId(List<Long> amount) {
        try {
            StripeClient client = new StripeClient(this.apiKey);

            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(2L)
                            .setRecurring(
                                    PriceCreateParams.Recurring.builder()
                                            .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                            .build()
                            )
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                            )
                            .build();

            Price price = client.v1().prices().create(params);
            return price.getId();
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


    // Assignment 1
    private String createPrice(Long amount) {
        try {
            StripeClient client = new StripeClient(this.apiKey);

            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(amount)
                            .setRecurring(
                                    PriceCreateParams.Recurring.builder()
                                            .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                            .build()
                            )
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                            )
                            .build();

            Price price = client.v1().prices().create(params);
            return price.getId();
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
