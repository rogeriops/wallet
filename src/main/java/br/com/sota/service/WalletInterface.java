package br.com.sota.service;

import br.com.sota.dto.WalletRequest;
import br.com.sota.dto.WalletResponse;

import java.math.BigDecimal;

public interface WalletInterface {

    WalletResponse createWallet(WalletRequest  walletRequest);

    WalletResponse getBalance(Long walletId);
}
