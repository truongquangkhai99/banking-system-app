package com.unvise.bankingsystemapp.exception.web.rest;

import com.unvise.bankingsystemapp.exception.ExchangeRateAlreadyExistException;
import com.unvise.bankingsystemapp.exception.ResourceNotFoundException;
import com.unvise.bankingsystemapp.exception.TransactionFailedException;
import com.unvise.bankingsystemapp.exception.web.dto.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ApiErrorDto> handleRuntimeException(RuntimeException e, WebRequest request) {
        ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                .message("RuntimeException message: " + e.getMessage())
                .fields(Map.of(
                        "Class: ", e.getClass(),
                        "LocalizedMessage: ", e.getLocalizedMessage()
                ))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(apiErrorDto, apiErrorDto.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ApiErrorDto>> onConstraintValidationException(ConstraintViolationException e) {
        List<ApiErrorDto> apiErrorDtos = e.getConstraintViolations().stream()
                .map(violation -> ApiErrorDto.builder()
                        .message(violation.getMessage())
                        .fields(Map.of("path", violation.getPropertyPath().toString()))
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
                )
                .collect(Collectors.toList());
        return new ResponseEntity<>(apiErrorDtos, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ApiErrorDto>> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ApiErrorDto> apiErrorDtos = e.getBindingResult().getFieldErrors().stream()
                .map(error -> ApiErrorDto.builder()
                        .message(error.getDefaultMessage())
                        .fields(Map.of(error.getField(), Objects.requireNonNull(error.getDefaultMessage())))
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
                )
                .collect(Collectors.toList());
        return new ResponseEntity<>(apiErrorDtos, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiErrorDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
                                                                                WebRequest request) {
        ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                .message("Invalid json. Check the json request for syntax errors.")
                .fields(Map.of(
                        "Class: ", e.getClass(),
                        "LocalizedMessage: ", e.getLocalizedMessage()
                ))
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(apiErrorDto, apiErrorDto.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiErrorDto> handleResourceNotFound(ResourceNotFoundException e, WebRequest request) {
        ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                .message("Can't find resource: " + e.getResourceName())
                .fields(e.getFieldNameAndValue())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(apiErrorDto, apiErrorDto.getStatus());
    }

    @ExceptionHandler(TransactionFailedException.class)
    protected ResponseEntity<ApiErrorDto> handleTransaction(TransactionFailedException e, WebRequest request) {
        ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                .message("Can't manage transaction. " + e.getMessage())
                .fields(e.getFieldNameAndValue())
                .status(HttpStatus.FORBIDDEN)
                .build();
        return new ResponseEntity<>(apiErrorDto, apiErrorDto.getStatus());
    }

    @ExceptionHandler(ExchangeRateAlreadyExistException.class)
    protected ResponseEntity<ApiErrorDto> handleExchangeRate(ExchangeRateAlreadyExistException e, WebRequest request) {
        ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                .message("Can't create new Exchange rate. "
                        + e.getMessage())
                .fields(e.getFieldNameAndValue())
                .status(HttpStatus.FORBIDDEN)
                .build();
        return new ResponseEntity<>(apiErrorDto, apiErrorDto.getStatus());
    }

}
