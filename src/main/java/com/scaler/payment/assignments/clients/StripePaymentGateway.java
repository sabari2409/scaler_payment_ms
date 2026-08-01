package com.scaler.payment.assignments.clients;

import com.scaler.payment.assignments.dto.stripe.SessionDto;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StripePaymentGateway {

    @Value("${stripe.key}")
    public String apiKey;

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
