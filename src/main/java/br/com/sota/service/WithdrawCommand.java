package br.com.sota.service;

import br.com.sota.dto.Request;
import br.com.sota.dto.Response;
import br.com.sota.dto.WalletTransactionRequest;
import br.com.sota.entity.TransactionType;
import br.com.sota.entity.Wallet;
import br.com.sota.entity.WalletTransaction;
import br.com.sota.exeception.WalletInsufficentFunds;
import br.com.sota.repository.WalletRepository;
import br.com.sota.repository.WalletTransactionRepository;
import br.com.sota.service.helper.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
@Component
public class WithdrawCommand implements TransactionCommand {

    private WalletTransaction walletTransaction;
    private Wallet wallet;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;
    @Autowired
    private WalletValidator walletValidator;

    @Override
    public void execute(Request request, String idempotencyKey) throws Exception {
        WalletTransactionRequest requestData = (WalletTransactionRequest) request;
        Wallet wallet = walletValidator.validateWalletId(requestData.getWalletId());
        walletValidator.validateIdempotecyKey(wallet,idempotencyKey);
        walletValidator.validateBalance(wallet,requestData.getAmount());
        this.walletTransaction = TransactionFactory.createWalletTransaction(requestData, wallet, String.valueOf(TransactionType.DEBIT), idempotencyKey);
        walletTransactionRepository.save(walletTransaction);
        wallet.setCurrentBalance(wallet.getCurrentBalance().subtract(walletTransaction.getAmount()));
        walletTransactionRepository.save(walletTransaction);
    }

    @Override
    public Response getResponse() {
        return TransactionFactory.createWalletResponse(walletTransaction, wallet);
    }
}
