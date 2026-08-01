package com.scaler.payment.assignments.controller.razorpay;

import com.scaler.payment.assignments.dto.razorpay.RefundRequestDto;
import com.scaler.payment.assignments.service.assignments.IRefundService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class RefundController {

    @Autowired
    private IRefundService refundService;

    @PostMapping("issueRefund")
    public String issueRefund(@RequestBody RefundRequestDto requestDto) {
        return this.refundService.issueRefund(requestDto.getAmount(), requestDto.getReceipt());
    }

    @PatchMapping("/updateRefund/{refundId}")
    public String updateRefund(@RequestBody RefundRequestDto requestDto, @PathVariable String refundId) {
        if (requestDto.getAmount() == null) throw new RuntimeException("Amount cannot be null");
        if (requestDto.getReceipt() == null) throw new RuntimeException("Receipt not available");
        if (requestDto.getRefundSpeed() == null) throw new RuntimeException("Refund sped is not available");
        JSONObject object = new JSONObject();
        object.put("amount", requestDto.getAmount());
        object.put("receipt", requestDto.getReceipt());
        object.put("speed", requestDto.getRefundSpeed());
        return this.refundService.updateRefund(refundId, object);
    }

}
