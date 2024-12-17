package br.com.sota.exeception;

public class WalletNotFound extends RuntimeException{

    public WalletNotFound() {

    }

    public WalletNotFound(String message) {
        super(message);
    }

}