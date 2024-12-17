package br.com.sota.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletResponse {

    private Long walletId;
    private String customerName;
    private String document;
    private BigDecimal currentBalance;

    public WalletResponse() {
    }

    public WalletResponse(Long walletId, BigDecimal currentBalance, String customerName, String document) {
        this.walletId=walletId;
        this.currentBalance=currentBalance;
        this.customerName=customerName;
        this.document=document;
    }

    public WalletResponse(Long walletId, BigDecimal currentBalance) {
        this.walletId = walletId;
        this.currentBalance = currentBalance;
    }


    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
