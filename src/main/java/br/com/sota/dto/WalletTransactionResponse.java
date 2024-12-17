package br.com.sota.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletTransactionResponse implements  Response{

    private Long transactionId;
    private String idempotencyKey;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private Long walletId;

    public WalletTransactionResponse() {
    }

    public WalletTransactionResponse(Long transactionId, String idempotencyKey, LocalDateTime createdAt, BigDecimal amount, Long walletId) {
        this.transactionId = transactionId;
        this.idempotencyKey = idempotencyKey;
        this.createdAt = createdAt;
        this.amount = amount;
        this.walletId = walletId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }
}
