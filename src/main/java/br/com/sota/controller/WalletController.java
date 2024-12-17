package br.com.sota.controller;

import br.com.sota.dto.WalletErrorResponse;
import br.com.sota.dto.WalletRequest;
import br.com.sota.dto.WalletResponse;
import br.com.sota.exeception.ErrorDetails;
import br.com.sota.service.WalletService;
import br.com.sota.service.WalletTransactionServiceF;
import br.com.sota.service.util.JsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Wallet")
@RestController
@RequiredArgsConstructor
public class WalletController {

    final Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    @Operation(summary = "Allow the creation of wallets for users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execute create wallet with success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletErrorResponse.class))
            }),
            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletErrorResponse.class))
            }),
    })


    @PostMapping()
    public ResponseEntity<?> createWallet(@RequestHeader("idempotencyKey") String idempotencyKey,
                                                       @RequestBody WalletRequest request) {

        log.info("WalletService::createWallet {} - request - {}", JsonUtil.objectToJson(request));

        WalletResponse walletResponse =  walletService.createWallet(request);
        log.info("WalletService::createWallet {} - response - {}", JsonUtil.objectToJson(walletResponse));
        return ResponseEntity.ok(walletResponse);
    }

    @Operation(summary = "Retrieve the current balance of a user's wallet.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execute get current balance from wallet with success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
    })

    @GetMapping("/currentBalance/{walletId}")
    public ResponseEntity<WalletResponse> balance(@PathVariable("walletId") Long walletId) {

        return ResponseEntity.ok(walletService.getBalance(walletId));
    }

    @Operation(summary = "Retrieve the current balance of a user's wallet.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execute get current balance from wallet with success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
    })

    @GetMapping("/retrieveHistoricalBalance/{walletId}/{historicalDate}")
    public ResponseEntity<WalletResponse> retrieveHistoricalBalance(@PathVariable("walletId") Long walletId,
                                                                    @PathVariable(value = "historicalDate")
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime historicalDate) {

        return ResponseEntity.ok(walletService.getHistoricalBalance(walletId, historicalDate));
    }

}
