package br.com.sota.service;

import br.com.sota.dto.Request;
import br.com.sota.dto.Response;
import br.com.sota.dto.WalletTransactionTransferRequest;
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
@Component
public class TransferCommand implements TransactionCommand {

    private WalletTransaction payerTransaction;
    private WalletTransaction receiverTransaction;
    private Wallet walletPayer;
    private Wallet walletReceiver;
    private String idempotencyKey;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletValidator walletValidator;

    @Override
    public void execute(Request request, String idempotencyKey) throws Exception {
        this.idempotencyKey=idempotencyKey;
        WalletTransactionTransferRequest requestData = (WalletTransactionTransferRequest) request;
        this.walletPayer = walletValidator.validateWalletId(requestData.getWalletIdPayer());
        walletValidator.validateIdempotecyKey(walletPayer,idempotencyKey);
        walletValidator.validateBalance(this.walletPayer,requestData.getAmount());
        this.doTransaction(requestData,walletPayer, TransactionType.DEBIT,idempotencyKey);

        this.walletReceiver = walletValidator.validateWalletId(requestData.getWalletIdReceiver());
        this.doTransaction(requestData,walletReceiver, TransactionType.CREDIT,idempotencyKey);
    }

    private void doTransaction(WalletTransactionTransferRequest requestData, Wallet wallet, TransactionType transactionType, String idempotencyKey) {
        this.walletTransactionRepository.save(TransactionFactory.
                createWalletTransferTransaction(requestData, wallet, String.valueOf(TransactionType.DEBIT), idempotencyKey));
        this.walletPayer.setCurrentBalance(walletPayer.getCurrentBalance().subtract(requestData.getAmount()));
        this.walletRepository.save(walletPayer);
    }

    @Override
    public Response getResponse() {
        return TransactionFactory.createWalletTransactionTransferResponse(payerTransaction,walletPayer,
                receiverTransaction, walletReceiver, idempotencyKey);
    }

}