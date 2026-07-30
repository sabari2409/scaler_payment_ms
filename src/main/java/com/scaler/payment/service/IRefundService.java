package com.scaler.payment.service;

import org.json.JSONObject;

public interface IRefundService {
    String issueRefund(Double amount, String receipt);
    String updateRefund(String refundId, JSONObject jsonObject);
}