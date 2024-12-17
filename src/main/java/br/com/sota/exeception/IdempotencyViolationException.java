package br.com.sota.exeception;

public class IdempotencyViolationException extends RuntimeException{

    public IdempotencyViolationException() {

    }

    public IdempotencyViolationException(String message) {
        super(message);
    }

}