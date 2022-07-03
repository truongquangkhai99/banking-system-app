package com.unvise.bankingsystemapp.deposit.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.deposit.DepositService;
import com.unvise.bankingsystemapp.deposit.web.dto.DepositDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/deposit")
@RequiredArgsConstructor
public class DepositRestController {

    private final DepositService depositService;

    @GetMapping
    public ResponseEntity<List<DepositDto>> getDeposits() {
        return new ResponseEntity<>(depositService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepositDto> getDeposit(@PathVariable Long id) {
        return new ResponseEntity<>(depositService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DepositDto> addDeposit(@Validated({View.New.class})
                                                 @RequestBody DepositDto depositDto) {
        return new ResponseEntity<>(depositService.save(depositDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepositDto> fullyUpdateDeposit(@PathVariable Long id,
                                                         @Validated({View.Update.class})
                                                         @RequestBody DepositDto depositDto) {
        return new ResponseEntity<>(depositService.updateById(id, depositDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepositDto> deleteDeposit(@PathVariable Long id) {
        return new ResponseEntity<>(depositService.deleteById(id), HttpStatus.OK);
    }

}
