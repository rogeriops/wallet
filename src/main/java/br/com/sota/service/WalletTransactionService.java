package br.com.sota.service;

import br.com.sota.dto.Request;
import br.com.sota.dto.Response;
import br.com.sota.exeception.DuplicateTransactionException;
import br.com.sota.exeception.InternalException;
import br.com.sota.exeception.WalletInsufficentFunds;
import br.com.sota.exeception.WalletNotFound;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Configurable
public class WalletTransactionService {

    final Logger log = LoggerFactory.getLogger(WalletTransactionService.class);

    public Response executeTransaction(TransactionCommand command, String idempotencyKey, Request request) {
        try {

            if (command instanceof DepositCommand) {
                command.execute(request, idempotencyKey);
                return command.getResponse();
            } else if (command instanceof WithdrawCommand) {
                command.execute(request, idempotencyKey);
                return command.getResponse();
            } else if (command instanceof TransferCommand) {
                command.execute(request, idempotencyKey);
                return command.getResponse();
            } else{
                return null;
            }


        } catch (WalletInsufficentFunds | WalletNotFound | DuplicateTransactionException ex) {
            log.error("WalletTransactionService::executeTransaction - idempotencyKey {}", ex.getMessage(),  idempotencyKey);
            throw ex;
        } catch (Throwable ex) {
            log.error("WalletTransactionService::executeTransaction - idempotencyKey {}", ex.getMessage(),  idempotencyKey);
            throw new InternalException(ex.getMessage());
        }

    }
}
