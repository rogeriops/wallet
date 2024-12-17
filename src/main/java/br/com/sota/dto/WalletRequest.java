package br.com.sota.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletRequest {

    @NotNull
    @Size(min = 3, max = 120, message = "Name must have min 3 and max 120 characters")
    private String customerName;

    @NotNull
    private String document;

    public WalletRequest() {
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
}
