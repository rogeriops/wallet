package br.com.sota.exeception;

public class WalletInsufficentFunds extends RuntimeException{

    public WalletInsufficentFunds() {

    }

    public WalletInsufficentFunds(String message) {
        super(message);
    }

}