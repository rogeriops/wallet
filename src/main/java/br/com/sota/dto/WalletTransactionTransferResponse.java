package br.com.sota.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletTransactionTransferResponse implements  Response{


    private String idempotencyKey;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private WalletTransactionResponse payer;
    private WalletTransactionResponse receiver;

    public WalletTransactionTransferResponse() {
    }

    public WalletTransactionTransferResponse(String idempotencyKey, LocalDateTime createdAt, BigDecimal amount, WalletTransactionResponse payer, WalletTransactionResponse receiver) {
        this.idempotencyKey = idempotencyKey;
        this.createdAt = createdAt;
        this.amount = amount;
        this.payer = payer;
        this.receiver = receiver;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public WalletTransactionResponse getPayer() {
        return payer;
    }

    public void setPayer(WalletTransactionResponse payer) {
        this.payer = payer;
    }

    public WalletTransactionResponse getReceiver() {
        return receiver;
    }

    public void setReceiver(WalletTransactionResponse receiver) {
        this.receiver = receiver;
    }
}
