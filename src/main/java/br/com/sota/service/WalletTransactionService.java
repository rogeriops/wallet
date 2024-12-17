package br.com.sota.service;

import br.com.sota.dto.WalletTransactionRequest;
import br.com.sota.dto.WalletTransactionResponse;
import br.com.sota.dto.WalletTransactionTransferRequest;
import br.com.sota.dto.WalletTransactionTransferResponse;
import br.com.sota.entity.TransactionType;
import br.com.sota.entity.Wallet;
import br.com.sota.entity.WalletTransaction;
import br.com.sota.exeception.DuplicateTransactionException;
import br.com.sota.exeception.InternalException;
import br.com.sota.exeception.WalletInsufficentFunds;
import br.com.sota.exeception.WalletNotFound;
import br.com.sota.repository.WalletRepository;
import br.com.sota.repository.WalletTransactionRepository;
import br.com.sota.service.helper.TransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class WalletTransactionService {

    final Logger log = LoggerFactory.getLogger(WalletTransactionService.class);

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired

    private WalletRepository walletRepository;

    public WalletTransactionResponse createDepositTransaction(WalletTransactionRequest request, String idempotencyKey) {
        try {

            Wallet wallet = walletRepository.findByWalletId(request.getWalletId())
                    .orElseThrow(() -> new WalletNotFound("Wallet with not found with given walletId " + request.getWalletId()));

            walletTransactionRepository.findByIdempotencyKeyAndWallet(idempotencyKey, wallet)
                    .ifPresent(t -> {
                        throw new DuplicateTransactionException("Transaction with the same idempotency key already exists");
                    });

            WalletTransaction walletTransaction = TransactionFactory.createWalletTransaction(request, wallet, String.valueOf(TransactionType.CREDIT),idempotencyKey);

            walletTransaction = walletTransactionRepository.save(walletTransaction);

            wallet.setCurrentBalance(wallet.getCurrentBalance().add(walletTransaction.getAmount()));

            walletRepository.save(wallet);

            return TransactionFactory.createWalletResponse(walletTransaction, wallet);

        }catch (DuplicateTransactionException | WalletNotFound ex){
            log.error("WalletTransactionResponse::processDeposit {} - walletId {} - idempotencyKey {}", ex.getMessage(), request.getWalletId(), idempotencyKey);
            throw ex;
        } catch(Throwable ex){
            log.error("WalletTransactionResponse::processDeposit {}- walletId {} - idempotencyKey {}", ex.getMessage(), request.getWalletId(), idempotencyKey);
            throw new InternalException(ex.getMessage());
        }
    }

    public WalletTransactionResponse createWithdrawTransaction(WalletTransactionRequest request, String idempotencyKey){
        try {
            Wallet wallet = walletRepository.findByWalletId(request.getWalletId())
                    .orElseThrow(() -> new WalletNotFound("Wallet not found with walletId" + request.getWalletId()));

            walletTransactionRepository.findByIdempotencyKeyAndWallet(idempotencyKey,wallet)
                    .ifPresent(t -> {
                        throw new DuplicateTransactionException("Transaction with the same idempotency key already exists");
                    });

            Optional.ofNullable(wallet)
                    .map(w -> w.getCurrentBalance().compareTo(request.getAmount()) >= 0)
                    .orElseThrow(() -> new WalletInsufficentFunds("Insufficient wallet balance " + request.getWalletId()));

            WalletTransaction walletTransaction = TransactionFactory.createWalletTransaction(request, wallet, String.valueOf(TransactionType.CREDIT),idempotencyKey);

            walletTransaction = walletTransactionRepository.save(walletTransaction);

            wallet.setCurrentBalance(wallet.getCurrentBalance().subtract(walletTransaction.getAmount()));

            walletRepository.save(wallet);

            return TransactionFactory.createWalletResponse(walletTransaction, wallet);
        }catch(WalletInsufficentFunds | WalletNotFound | DuplicateTransactionException ex){
            log.error("WalletTransactionResponse::processWithdraw {} - walletId {} - idempotencyKey {}", ex.getMessage(), request.getWalletId(), idempotencyKey);
            throw ex;
        } catch(Throwable ex){
            log.error("WalletTransactionResponse::processWithdraw {} - walletId {} - idempotencyKey {}", ex.getMessage(), request.getWalletId(), idempotencyKey);
            throw new InternalException(ex.getMessage());
        }
    }

    public WalletTransactionTransferResponse createTransferTransaction(WalletTransactionTransferRequest request, String idempotencyKey){
        try{
            Wallet walletPayer = walletRepository.findByWalletId(request.getWalletIdPayer())
                    .orElseThrow(() -> new WalletNotFound("Payer wallet with not found with given walletId " + request.getWalletIdPayer()));

            walletTransactionRepository.findByIdempotencyKeyAndWallet(idempotencyKey,walletPayer)
                    .ifPresent(t -> {
                        throw new DuplicateTransactionException("Transaction with the same idempotency key already exists");
                    });

            Optional.ofNullable(walletPayer)
                    .map(w -> w.getCurrentBalance().compareTo(request.getAmount()) >= 0)
                    .orElseThrow(() -> new WalletInsufficentFunds("Insufficient payer wallet balance " + request.getWalletIdPayer()));

            Wallet walletReceiver = walletRepository.findByWalletId(request.getWalletIdReceiver())
                    .orElseThrow(() -> new WalletNotFound("Receiver wallet with not found with given walletId " + request.getWalletIdReceiver()));

           //payer

            WalletTransaction walletTransactionPayer = TransactionFactory.createWalletTransferTransaction(request, walletPayer, String.valueOf(TransactionType.DEBIT), idempotencyKey);

            walletTransactionPayer = walletTransactionRepository.save(walletTransactionPayer);

            walletPayer.setCurrentBalance(walletPayer.getCurrentBalance().subtract(request.getAmount()));

            walletPayer = walletRepository.save(walletPayer);

            //receiver

            WalletTransaction walletTransactionReceiver = TransactionFactory.createWalletTransferTransaction(request,walletReceiver, String.valueOf(TransactionType.CREDIT),idempotencyKey);

            walletTransactionReceiver = walletTransactionRepository.save(walletTransactionReceiver);

            walletReceiver.setCurrentBalance(walletReceiver.getCurrentBalance().add(request.getAmount()));

            walletReceiver = walletRepository.save(walletReceiver);

            return TransactionFactory.createWalletTransactionTransferResponse(walletTransactionPayer, walletPayer, walletTransactionReceiver, walletReceiver,idempotencyKey);
        }catch(WalletInsufficentFunds | WalletNotFound | DuplicateTransactionException ex){
            log.error("WalletTransactionResponse::processTransfer {} - idempotencyKey {}", ex.getMessage(),  idempotencyKey);
            throw ex;
        }catch(Throwable ex){
            log.error("WalletTransactionResponse::processTransfer {} - idempotencyKey {}", ex.getMessage(),  idempotencyKey);
            throw new InternalException(ex.getMessage());
        }
    }
}
