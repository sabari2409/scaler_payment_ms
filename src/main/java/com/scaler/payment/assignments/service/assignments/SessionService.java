package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.clients.StripePaymentGateway;
import com.scaler.payment.assignments.dto.stripe.SessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService implements ISessionService {

    @Autowired
    private StripePaymentGateway stripePaymentGateway;

    public SessionDto createSession(String successUrl, List<Long> amounts, List<String> productNames,
                                    List<Long> quantities) {
        return this.stripePaymentGateway.createSession(successUrl, amounts, productNames, quantities);
    }
}
