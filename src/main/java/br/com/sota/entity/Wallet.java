package br.com.sota.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "wallet")
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long walletId;
    private BigDecimal currentBalance;
    private String customerName;
    private String document;

    @OneToMany(mappedBy = "wallet")
    private List<WalletTransaction> transactions;

     public Wallet(){

     }

    public Wallet(BigDecimal currentBalance, String customerName, String document) {
        this.currentBalance=currentBalance;
        this.customerName=customerName;
        this.document=document;
    }

    public Wallet(Long walletId,String customerName,BigDecimal currentBalance) {
        this.walletId=walletId;
        this.customerName=customerName;
        this.currentBalance=currentBalance;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
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

    public List<WalletTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<WalletTransaction> transactions) {
        this.transactions = transactions;
    }
}



