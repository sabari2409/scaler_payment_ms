package com.scaler.payment.assignments.controller.stripe;

import com.scaler.payment.assignments.dto.stripe.CreateSessionDto;
import com.scaler.payment.assignments.dto.stripe.SessionDto;
import com.scaler.payment.assignments.service.assignments.ISessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private ISessionService sessionService;

    @PostMapping
    public SessionDto createSession(@RequestBody CreateSessionDto req) {
        return this.sessionService.createSession(
                req.getSuccessUrl(), req.getAmounts(), req.getProductNames(), req.getQuantities()
        );
    }
}