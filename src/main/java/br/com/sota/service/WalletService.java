package br.com.sota.service;

import br.com.sota.dto.WalletRequest;
import br.com.sota.dto.WalletResponse;
import br.com.sota.entity.Wallet;
import br.com.sota.exeception.InternalException;
import br.com.sota.exeception.WalletNotFound;
import br.com.sota.repository.WalletRepository;
import br.com.sota.service.helper.WalletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletService implements WalletInterface{

    final Logger log = LoggerFactory.getLogger(WalletService.class);

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public WalletResponse createWallet(WalletRequest walletRequest) {
        try{
            Wallet wallet = walletRepository.findByDocument(walletRequest.getDocument())
                    .orElseGet(() -> {
                         Wallet entity  = WalletFactory.createWalletByRequest(walletRequest);
                         return walletRepository.save(entity);
                    });
            return WalletFactory.createWalletResponse(wallet);
        }catch(Throwable ex){
            log.error("WalletService::createWallet {} - document {} ", ex.getMessage(), walletRequest.getDocument());
            throw new InternalException(ex.getMessage());
        }
    }

    @Override
    public WalletResponse getBalance(Long walletId) {
        try {
            Wallet wallet = walletRepository.findByWalletId(walletId)
                    .orElseThrow(() -> new WalletNotFound("Wallet with not found with given walletId " + walletId));
            return WalletFactory.createWalletBalanceResponse(wallet);
        }catch(WalletNotFound ex){
            log.error("WalletService::getBalance {} - walletId {} ", ex.getMessage(), walletId );
            throw ex;
        }catch(Throwable ex){
            log.error("WalletService::getBalance {} - walletId {} ", ex.getMessage(), walletId );
            throw new InternalException(ex.getMessage());
        }

    }

    public WalletResponse getHistoricalBalance(Long walletId, LocalDateTime historicalDate) {
        try{
            Wallet wallet = walletRepository.findWalletsWithBalanceByDateAndDocument(historicalDate,walletId)
                    .orElseThrow(() -> new WalletNotFound("Historical Balance with not found with given walletId " + walletId));
            return WalletFactory.createWalletBalanceResponse(wallet);
        }catch(WalletNotFound ex){
            log.error("WalletService::getHistoricalBalance {} - walletId {} ", ex.getMessage(), walletId );
            throw ex;
        }catch(Throwable ex){
            log.error("WalletService::getHistoricalBalance {} - walletId {} ", ex.getMessage(), walletId );
            throw new InternalException(ex.getMessage());
        }
    }
}
