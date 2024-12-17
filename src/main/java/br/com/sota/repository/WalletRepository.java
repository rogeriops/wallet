package br.com.sota.repository;

import br.com.sota.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByDocument(String document);

    Optional<Wallet> findByWalletId(Long walletId);

//    @Query(value = "select w.walletId, " +
//            "SUM(CASE WHEN wt.transactionType = 'CREDIT' THEN wt.amount ELSE -wt.amount END)) " +
//            "FROM Wallet w " +
//            "INNER JOIN w.transactions wt " +
//            "WHERE wt.createAt <= :date " +
//            "AND w.id = :walletId " + // Adiciona o filtro por ID da carteira
//            "GROUP BY w.walletId")
 //   Optional<Wallet> findWalletBalanceByDateAndId(@Param("date") LocalDateTime date, @Param("walletId") Long walletId);


    @Query(value = "SELECT new Wallet(w.walletId, w.customerName, " +
            "SUM(CASE WHEN wt.transactionType = 'CREDIT' THEN wt.amount ELSE -wt.amount END)) " +
            "FROM Wallet w " +
            "INNER JOIN w.transactions wt " +
            "WHERE wt.createAt <= :date " +
            "AND w.walletId = :walletId ")
    Optional<Wallet> findWalletsWithBalanceByDateAndDocument(LocalDateTime date, Long walletId);
}
