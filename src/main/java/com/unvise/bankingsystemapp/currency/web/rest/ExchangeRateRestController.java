package com.unvise.bankingsystemapp.currency.web.rest;

import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.currency.ExchangeRateService;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.currency.web.dto.ExchangeRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateRestController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRates() {
        return new ResponseEntity<>(exchangeRateService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRateDto> getExchangeRate(@PathVariable Long id) {
        return new ResponseEntity<>(exchangeRateService.getById(id), HttpStatus.OK);
    }

    @GetMapping(params = "toCurrency")
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRateByToCurrency(@RequestParam String toCurrency) {
        return new ResponseEntity<>(exchangeRateService.getByToCurrency(CurrencyType.valueOf(toCurrency)), HttpStatus.OK);
    }

    @GetMapping(params = "fromCurrency")
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRateByFromCurrency(@RequestParam String fromCurrency) {
        return new ResponseEntity<>(exchangeRateService.getByFromCurrency(CurrencyType.valueOf(fromCurrency)), HttpStatus.OK);
    }

    @GetMapping(params = {"fromCurrency", "toCurrency"})
    public ResponseEntity<ExchangeRateDto> getExchangeRateByFromCurrency(@RequestParam String fromCurrency,
                                                                         @RequestParam String toCurrency) {
        return new ResponseEntity<>(exchangeRateService.getByFromCurrencyAndToCurrency(
                CurrencyType.valueOf(fromCurrency),
                CurrencyType.valueOf(toCurrency)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExchangeRateDto> addExchangeRate(@Validated(View.New.class)
                                                           @RequestBody ExchangeRateDto exchangeRateDto) {
        return new ResponseEntity<>(exchangeRateService.save(exchangeRateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRateDto> fullyUpdateExchangeRate(@PathVariable Long id,
                                                                   @Validated(View.Update.class)
                                                                   @RequestBody ExchangeRateDto exchangeRateDto) {
        return new ResponseEntity<>(exchangeRateService.updateById(id, exchangeRateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExchangeRateDto> deleteExchangeRate(@PathVariable Long id) {
        return new ResponseEntity<>(exchangeRateService.deleteById(id), HttpStatus.OK);
    }

}
