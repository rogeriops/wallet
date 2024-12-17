package br.com.sota.exeception;

import java.time.LocalDateTime;

public class ErrorDetails {

    private LocalDateTime dateTime;
    private String message;
    private String details;

    public ErrorDetails() {

    }

    public ErrorDetails(LocalDateTime dateTime, String message, String details) {
        this.dateTime = dateTime;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
