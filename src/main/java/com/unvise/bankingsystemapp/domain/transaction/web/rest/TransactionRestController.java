package com.unvise.bankingsystemapp.domain.transaction.web.rest;

import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.transaction.transaction.TransactionService;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
@RequiredArgsConstructor
public class TransactionRestController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll() {
        return new ResponseEntity<>(transactionService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionDto> save(@Validated(View.New.class)
                                               @RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.save(transactionDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionDto> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.deleteById(id), HttpStatus.OK);
    }

}
