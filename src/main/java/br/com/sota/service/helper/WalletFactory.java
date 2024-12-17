package br.com.sota.service.helper;

import br.com.sota.dto.WalletRequest;
import br.com.sota.dto.WalletResponse;
import br.com.sota.entity.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Optional.*;

@Component
public class WalletFactory {

    public  static Wallet createWalletByRequest(WalletRequest request){

        return new Wallet(BigDecimal.ZERO,request.getCustomerName(),request.getDocument());
    }

    public  static WalletResponse createWalletResponse(Wallet wallet){

        return new WalletResponse(wallet.getWalletId(),wallet.getCurrentBalance(),wallet.getCustomerName(),wallet.getDocument());
    }

    public  static WalletResponse createWalletBalanceResponse(Wallet wallet){

        return new WalletResponse(wallet.getWalletId(), ofNullable(wallet.getCurrentBalance()).orElse(BigDecimal.ZERO));
    }

}
