package br.com.sota.controller;

import br.com.sota.dto.*;
import br.com.sota.exeception.ErrorDetails;
import br.com.sota.service.*;
import br.com.sota.service.util.JsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Wallet Transaction")
@RestController

public class WalletTransactionController {

    final Logger log = LoggerFactory.getLogger(WalletTransactionController.class);

//    @Autowired
//    public WalletTransactionService walletTransactionService;

    @Autowired
    public WalletTransactionServiceF walletTransactionService;
    @Autowired
    public DepositCommand depositCommand;
    @Autowired
    public WithdrawCommand withdrawCommand;
    @Autowired
    public TransactionCommand transactionCommand;


    @Operation(summary = "Enable users to deposit money into their wallets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execute credit with success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletTransactionResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
    })

    @PostMapping("/deposit")
    public ResponseEntity<WalletTransactionResponse> depositFunds(@RequestHeader("idempotencyKey") String idempotencyKey,
                                                                  @RequestBody WalletTransactionRequest request) {

        log.info("WalletTransactionService::depositFunds {} - request - {}", JsonUtil.objectToJson(request));

        WalletTransactionResponse response = (WalletTransactionResponse) walletTransactionService.executeTransaction(depositCommand,idempotencyKey,request);
        log.info("WalletTransactionService::depositFunds {} - response - {}", JsonUtil.objectToJson(request));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Enable users to withdraw money from their wallets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execute credit with success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletTransactionResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
    })

    @PostMapping("/withdraw")
    public ResponseEntity<WalletTransactionResponse> withdrawFunds(@RequestHeader("idempotencyKey") String idempotencyKey,
                                                        @RequestBody WalletTransactionRequest request) {
        log.info("WalletTransactionService::withdrawFunds {} - request - {}", JsonUtil.objectToJson(request));

        WalletTransactionResponse response = (WalletTransactionResponse)
                walletTransactionService.executeTransaction(withdrawCommand,idempotencyKey,request);

        log.info("WalletTransactionService::withdrawFunds {} - response - {}", JsonUtil.objectToJson(request));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Facilitate the transfer of money between user wallets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execute credit with success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletTransactionTransferResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
    })

    @PostMapping("/transfer")
    public ResponseEntity<WalletTransactionTransferResponse> transferFunds(@RequestHeader("idempotencyKey") String idempotencyKey,
                                                        @RequestBody WalletTransactionTransferRequest request) {

        log.info("WalletTransactionService::transferFunds {} - request - {}", JsonUtil.objectToJson(request));


        WalletTransactionTransferResponse response = (WalletTransactionTransferResponse)
                walletTransactionService.executeTransaction(transactionCommand,idempotencyKey,request);

        log.info("WalletTransactionService::transferFunds {} - response - {}", JsonUtil.objectToJson(request));
        return ResponseEntity.ok(response);
    }
}
