package br.com.sota.repository;

import br.com.sota.entity.Wallet;
import br.com.sota.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    Optional<WalletTransaction> findByIdempotencyKeyAndWallet(String idempotencyKey, Wallet wallet);
}
