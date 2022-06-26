package com.unvise.bankingsystemapp.account.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.unvise.bankingsystemapp.account.AccountService;
import com.unvise.bankingsystemapp.account.web.dto.AccountDto;
import com.unvise.bankingsystemapp.account.web.dto.AccountViews;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @JsonView(AccountViews.Details.class)
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return new ResponseEntity<>(accountService.getAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @JsonView(AccountViews.Details.class)
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getById(id), HttpStatus.OK);
    }

    @GetMapping(params = "currency")
    @JsonView(AccountViews.Details.class)
    public ResponseEntity<List<AccountDto>> getAccountByCurrency(@RequestParam String currency) {
        return new ResponseEntity<>(accountService.getByCurrency(CurrencyType.valueOf(currency)), HttpStatus.OK);
    }

    @PostMapping
    @JsonView(AccountViews.Details.class)
    public ResponseEntity<AccountDto> addAccount(@Validated({AccountViews.New.class})
                                                 @RequestBody AccountDto AccountDto) {
        return new ResponseEntity<>(accountService.save(AccountDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @JsonView(AccountViews.Details.class)
    public ResponseEntity<AccountDto> fullyUpdateAccount(@PathVariable Long id,
                                                         @RequestBody AccountDto AccountDto) {
        return new ResponseEntity<>(accountService.updateById(id, AccountDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @JsonView(AccountViews.Details.class)
    public ResponseEntity<AccountDto> deleteAccount(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.deleteById(id), HttpStatus.OK);
    }

}
