package br.com.sota.service;

import br.com.sota.dto.Request;
import br.com.sota.dto.Response;

public interface TransactionCommand {
    void execute(Request request, String idempotencyKey) throws Exception;

    Response getResponse();
}
