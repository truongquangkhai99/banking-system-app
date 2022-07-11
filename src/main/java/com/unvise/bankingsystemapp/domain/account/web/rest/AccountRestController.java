package com.unvise.bankingsystemapp.domain.account.web.rest;

import com.unvise.bankingsystemapp.domain.account.account.AccountService;
import com.unvise.bankingsystemapp.domain.account.history.AccountHistoryService;
import com.unvise.bankingsystemapp.domain.account.web.dto.AccountDto;
import com.unvise.bankingsystemapp.domain.account.web.dto.AccountHistoryDto;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;
    private final AccountHistoryService accountHistoryService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return new ResponseEntity<>(accountService.getAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getById(id), HttpStatus.OK);
    }

    @GetMapping(params = "currency")
    public ResponseEntity<List<AccountDto>> getAccountByCurrency(@RequestParam String currency) {
        return new ResponseEntity<>(accountService.getByCurrency(CurrencyType.valueOf(currency)), HttpStatus.OK);
    }

    @GetMapping("/account-history/{id}")
    public ResponseEntity<AccountHistoryDto> getAccountHistoryByAccountId(@PathVariable Long id) {
        return new ResponseEntity<>(accountHistoryService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> fullyUpdateAccount(@PathVariable Long id,
                                                         @Validated({View.Update.class})
                                                         @RequestBody AccountDto AccountDto) {
        return new ResponseEntity<>(accountService.updateById(id, AccountDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountDto> deleteAccount(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.deleteById(id), HttpStatus.OK);
    }

}
