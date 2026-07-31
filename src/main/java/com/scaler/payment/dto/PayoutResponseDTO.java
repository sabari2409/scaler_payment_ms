package com.scaler.payment.dto;


import lombok.Data;

@Data
public class PayoutResponseDTO {

    private String id;
    private String entity;
    private String fundAccountId;
    private Double amount;
    private String currency;
    private Integer fees;
    private Integer tax;
    private String status;
    private String utr;
    private String mode;
    private String purpose;
    private String referenceId;
    private String narration;
    private String batchId;
    private Integer created_at;
}


//{
//        "id": "pout_00000000000001",
//        "entity": "payout",
//        "fund_account_id": "fa_00000000000001",
//        "amount": 1000000,
//        "currency": "INR",
//        "notes": {
//        "notes_key_1":"Tea, Earl Grey, Hot",
//        "notes_key_2":"Tea, Earl Grey… decaf."
//        },
//        "fees": 0,
//        "tax": 0,
//        "status": "queued",
//        "utr": null,
//        "mode": "IMPS",
//        "purpose": "refund",
//        "reference_id": "Acme Transaction ID 12345",
//        "narration": "Acme Corp Fund Transfer",
//        "batch_id": null,
//        "status_details": {
//        "description": "Payout is queued as there is insufficient balance in your business account to process the payout",
//        "source": "business",
//        "reason": "low_balance"
//        }
//        "created_at": 1545383037
//        }