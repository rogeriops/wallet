package br.com.sota.dto;

import java.math.BigDecimal;

public class WalletTransactionRequest implements Request{

    private BigDecimal amount;
    private Long walletId;


    public WalletTransactionRequest() {
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
