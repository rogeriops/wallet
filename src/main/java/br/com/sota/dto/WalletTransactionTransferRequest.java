package br.com.sota.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletTransactionTransferRequest implements Request{

    private BigDecimal amount;

    private Long walletIdPayer;

    private Long walletIdReceiver;

    public WalletTransactionTransferRequest() {
    }

    public WalletTransactionTransferRequest(BigDecimal amount, Long payer, Long receiver) {
        this.amount = amount;
        this.walletIdPayer = payer;
        this.walletIdReceiver = receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getWalletIdPayer() {
        return walletIdPayer;
    }

    public void setWalletIdPayer(Long walletIdPayer) {
        this.walletIdPayer = walletIdPayer;
    }

    public Long getWalletIdReceiver() {
        return walletIdReceiver;
    }

    public void setWalletIdReceiver(Long walletIdReceiver) {
        this.walletIdReceiver = walletIdReceiver;
    }
}
