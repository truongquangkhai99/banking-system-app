package com.unvise.bankingsystemapp.exception;

import com.unvise.bankingsystemapp.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e, WebRequest request) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("RuntimeException message: " + e.getMessage())
                .fields(Map.of(
                        "Class: ", e.getClass(),
                        "LocalizedMessage: ", e.getLocalizedMessage()
                ))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
                                                                                WebRequest request) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Invalid json. Check the json request for syntax errors.")
                .fields(Map.of(
                        "Class: ", e.getClass(),
                        "LocalizedMessage: ", e.getLocalizedMessage()
                ))
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException e, WebRequest request) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Can't find resource: " + e.getResourceName())
                .fields(e.getFieldNameAndValue())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(TransactionFailedException.class)
    protected ResponseEntity<ApiResponse> handleTransaction(TransactionFailedException e, WebRequest request) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Can't manage transaction. " + e.getMessage())
                .fields(e.getFieldNameAndValue())
                .status(HttpStatus.FORBIDDEN)
                .build();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(ExchangeRateAlreadyExistException.class)
    protected ResponseEntity<ApiResponse> handleExchangeRate(ExchangeRateAlreadyExistException e, WebRequest request) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Can't create new Exchange rate. "
                        + e.getMessage())
                .fields(e.getFieldNameAndValue())
                .status(HttpStatus.FORBIDDEN)
                .build();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

}
