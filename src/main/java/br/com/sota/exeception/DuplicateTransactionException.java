package br.com.sota.exeception;

public class DuplicateTransactionException extends RuntimeException{

    public DuplicateTransactionException() {

    }

    public DuplicateTransactionException(String message) {
        super(message);
    }

}