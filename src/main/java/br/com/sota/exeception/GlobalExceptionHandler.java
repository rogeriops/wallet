package br.com.sota.exeception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletNotFound.class)
    ResponseEntity<ErrorDetails> walletNotFound(WalletNotFound walletNotFound, WebRequest webRequest){
        ErrorDetails errorDetails = getErrorDetails(walletNotFound.getMessage(), webRequest);
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WalletInsufficentFunds.class)
    ResponseEntity<ErrorDetails> walletInsufficentFunds(WalletInsufficentFunds walletInsufficentFunds, WebRequest webRequest){

        ErrorDetails errorDetails = getErrorDetails(walletInsufficentFunds.getMessage(), webRequest);
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DuplicateTransactionException.class)
    ResponseEntity<ErrorDetails> walletInsufficentFunds(DuplicateTransactionException duplicateTransactionException, WebRequest webRequest){

        ErrorDetails errorDetails = getErrorDetails(duplicateTransactionException.getMessage(), webRequest);
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalException.class)
    ResponseEntity<ErrorDetails> interalException(InternalException internalException, WebRequest webRequest){

        ErrorDetails errorDetails = getErrorDetails(internalException.getMessage(), webRequest);
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IdempotencyViolationException.class)
    ResponseEntity<ErrorDetails> IdempotencyViolationException(IdempotencyViolationException idempotencyViolationException, WebRequest webRequest){

        ErrorDetails errorDetails = getErrorDetails(idempotencyViolationException.getMessage(), webRequest);
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ErrorDetails getErrorDetails(String walletNotFound, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDateTime(LocalDateTime.now());
        errorDetails.setMessage(walletNotFound);
        errorDetails.setDetails(webRequest.getDescription(false));
        return errorDetails;
    }
}
