package br.com.sota.service;

import br.com.sota.entity.Wallet;
import br.com.sota.exeception.DuplicateTransactionException;
import br.com.sota.exeception.IdempotencyViolationException;
import br.com.sota.exeception.WalletInsufficentFunds;
import br.com.sota.exeception.WalletNotFound;
import br.com.sota.repository.WalletRepository;
import br.com.sota.repository.WalletTransactionRepository;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class WalletValidator {

    final Logger log = LoggerFactory.getLogger(WalletValidator.class);

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    public Wallet validateWalletId(Long walletId){
        log.info("WalletValidator::validateWalletId Validate walletId={}", walletId);
        return walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new WalletNotFound("Wallet not found with walletId " + walletId));
    }

    public void validateBalance(Wallet wallet, BigDecimal amount) {
        log.info("WalletValidator::validateBalance Validate balance walletId={}, amount={}", wallet.getWalletId(), amount);
        Optional.of(wallet)
                .filter(w -> w.getCurrentBalance().compareTo(amount) >= 0)
                .orElseThrow(() -> new WalletInsufficentFunds("Insufficient payer wallet balance " + wallet.getWalletId()));
    }

    public void validateIdempotecyKey(Wallet wallLet, String idempotencyKey) {
        log.info("WalletValidator::validateIdempotecyKey Validate idempotencyKey walletId={}, idempotencyKey={}", wallLet.getWalletId(), idempotencyKey);
        walletTransactionRepository.findByIdempotencyKeyAndWallet(idempotencyKey, wallLet)
                .ifPresent(t -> {
                    throw new IdempotencyViolationException("Transaction with the same idempotency key already exists");
                });
    }
}
