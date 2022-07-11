package com.unvise.bankingsystemapp.domain.currency.web;

import com.unvise.bankingsystemapp.domain.currency.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping
    public String exchangeRates(Model model) {

        model.addAttribute("exchangeRates", exchangeRateService.getAll());

        return "exchange_rate/exchange-rate-page";
    }

}
