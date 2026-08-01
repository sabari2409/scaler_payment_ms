package com.scaler.payment.assignments.clients;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
