package br.com.sota.service.helper;

import br.com.sota.dto.WalletTransactionRequest;
import br.com.sota.dto.WalletTransactionResponse;
import br.com.sota.dto.WalletTransactionTransferRequest;
import br.com.sota.dto.WalletTransactionTransferResponse;
import br.com.sota.entity.Wallet;
import br.com.sota.entity.WalletTransaction;

import org.springframework.stereotype.Component;

@Component
public class TransactionFactory {

    public static WalletTransaction createWalletTransaction(WalletTransactionRequest request, Wallet wallet, String transactionType, String idempotencyKey){
        return new WalletTransaction(transactionType, request.getAmount(), wallet,idempotencyKey);

    }

    public static WalletTransactionResponse createWalletResponse(WalletTransaction walletTransaction, Wallet wallet){
        return new WalletTransactionResponse(walletTransaction.getTransactionId(),
                walletTransaction.getIdempotencyKey(), walletTransaction.getCreateAt(),
                walletTransaction.getAmount(), walletTransaction.getWallet().getWalletId());
    }

    public static WalletTransaction createWalletTransferTransaction(WalletTransactionTransferRequest request,
                                                                    Wallet wallet, String transactionType,String idempotencyKey){
        return new WalletTransaction(transactionType, request.getAmount(), wallet, idempotencyKey);

    }

    public static WalletTransactionTransferResponse createWalletTransactionTransferResponse(WalletTransaction walletTransactionPayer, Wallet walletPayer,
                                                                                            WalletTransaction walletTransactionReceiver, Wallet walletReceiver,
                                                                                            String idempotencyKey){


        WalletTransactionResponse payer = new WalletTransactionResponse();
        payer.setTransactionId(walletTransactionPayer.getTransactionId());
        payer.setWalletId(walletTransactionPayer.getWallet().getWalletId());

        WalletTransactionResponse receiver = new WalletTransactionResponse();
        receiver.setTransactionId(walletTransactionReceiver.getTransactionId());
        receiver.setWalletId(walletTransactionReceiver.getWallet().getWalletId());

        return new WalletTransactionTransferResponse(idempotencyKey,walletTransactionPayer.getCreateAt(), walletTransactionPayer.getAmount(),
                payer, receiver);

    }


}

