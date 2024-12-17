package br.com.sota.service;

import br.com.sota.dto.Request;
import br.com.sota.dto.Response;
import br.com.sota.dto.WalletTransactionRequest;
import br.com.sota.entity.TransactionType;
import br.com.sota.entity.Wallet;
import br.com.sota.entity.WalletTransaction;
import br.com.sota.repository.WalletRepository;
import br.com.sota.repository.WalletTransactionRepository;
import br.com.sota.service.helper.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DepositCommand implements TransactionCommand {

    private WalletTransaction walletTransaction;

    private Wallet wallet;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;
    @Autowired
    private WalletValidator walletValidator;
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void execute(Request request, String idempotencyKey) throws Exception {
        WalletTransactionRequest requestData = (WalletTransactionRequest) request;
        Wallet wallet = this.walletValidator.validateWalletId(requestData.getWalletId());
        this.walletValidator.validateIdempotecyKey(wallet,idempotencyKey);
        this.walletTransaction = TransactionFactory.createWalletTransaction(requestData, wallet, String.valueOf(TransactionType.CREDIT),idempotencyKey);
        walletTransactionRepository.save(walletTransaction);
        wallet.setCurrentBalance(wallet.getCurrentBalance().add(walletTransaction.getAmount()));
        this.walletRepository.save(wallet);
    }

    @Override
    public Response getResponse() {
        return TransactionFactory.createWalletResponse(walletTransaction, wallet);
    }
}

