package com.unvise.bankingsystemapp.currency.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.unvise.bankingsystemapp.currency.ExchangeRateService;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.currency.web.dto.ExchangeRateDto;
import com.unvise.bankingsystemapp.currency.web.dto.ExchangeRateViews;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping
    @JsonView(ExchangeRateViews.Details.class)
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRates() {
        return new ResponseEntity<>(exchangeRateService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @JsonView(ExchangeRateViews.Details.class)
    public ResponseEntity<ExchangeRateDto> getExchangeRate(@PathVariable Long id) {
        return new ResponseEntity<>(exchangeRateService.getById(id), HttpStatus.OK);
    }

    @GetMapping(params = "toCurrency")
    @JsonView(ExchangeRateViews.Details.class)
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRateByToCurrency(@RequestParam String toCurrency) {
        return new ResponseEntity<>(exchangeRateService.getByToCurrency(CurrencyType.valueOf(toCurrency)), HttpStatus.OK);
    }

    @GetMapping(params = "fromCurrency")
    @JsonView(ExchangeRateViews.Details.class)
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRateByFromCurrency(@RequestParam String fromCurrency) {
        return new ResponseEntity<>(exchangeRateService.getByFromCurrency(CurrencyType.valueOf(fromCurrency)), HttpStatus.OK);
    }

    @GetMapping(params = {"fromCurrency", "toCurrency"})
    @JsonView(ExchangeRateViews.Details.class)
    public ResponseEntity<ExchangeRateDto> getExchangeRateByFromCurrency(@RequestParam String fromCurrency,
                                                                         @RequestParam String toCurrency) {
        return new ResponseEntity<>(exchangeRateService.getByFromCurrencyAndToCurrency(
                CurrencyType.valueOf(fromCurrency),
                CurrencyType.valueOf(toCurrency)),
                HttpStatus.OK);
    }

    @PostMapping
    @JsonView(ExchangeRateViews.Details.class)
    public ResponseEntity<ExchangeRateDto> addExchangeRate(@Validated(ExchangeRateViews.New.class)
                                                               @RequestBody ExchangeRateDto exchangeRateDto) {
        return new ResponseEntity<>(exchangeRateService.save(exchangeRateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @JsonView(ExchangeRateViews.Details.class)
    public ResponseEntity<ExchangeRateDto> fullyUpdateExchangeRate(@PathVariable Long id,
                                                                   @RequestBody ExchangeRateDto exchangeRateDto) {
        return new ResponseEntity<>(exchangeRateService.updateById(id, exchangeRateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @JsonView(ExchangeRateViews.Details.class)
    public ResponseEntity<ExchangeRateDto> deleteExchangeRate(@PathVariable Long id) {
        return new ResponseEntity<>(exchangeRateService.deleteById(id), HttpStatus.OK);
    }

}
