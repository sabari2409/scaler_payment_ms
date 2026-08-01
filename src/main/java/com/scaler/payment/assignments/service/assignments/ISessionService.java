package com.scaler.payment.assignments.service.assignments;

import com.scaler.payment.assignments.dto.stripe.SessionDto;

import java.util.List;

public interface ISessionService {
    SessionDto createSession(String successUrl, List<Long> amounts, List<String> productNames, List<Long> quantities);
}