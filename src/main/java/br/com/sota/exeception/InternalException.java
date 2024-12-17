package br.com.sota.exeception;

public class InternalException extends RuntimeException{

    public InternalException() {

    }

    public InternalException(String message) {
        super(message);
    }

}