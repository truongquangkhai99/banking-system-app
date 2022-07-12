package com.unvise.bankingsystemapp.domain.credit.web.mvc;

import com.unvise.bankingsystemapp.domain.common.View;
import com.unvise.bankingsystemapp.domain.credit.CreditService;
import com.unvise.bankingsystemapp.domain.credit.web.dto.CreditDto;
import com.unvise.bankingsystemapp.domain.credit.web.dto.RepayCreditDto;
import com.unvise.bankingsystemapp.domain.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.domain.transaction.transaction.TransactionService;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDetailsDto;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("credit")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;
    private final TransactionService transactionService;

    @PostMapping
    public String takeCredit(@ModelAttribute("credit")
                             @Validated(View.New.class) CreditDto creditDto,
                             BindingResult bindingResult,
                             RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
            return "redirect:/account";
        }

        try {
            creditService.save(creditDto);
        } catch (Exception e) {
            attributes.addFlashAttribute("errors", e.getLocalizedMessage());

            return "redirect:/account";
        }

        attributes.addFlashAttribute("success", "Success! You take a new credit.");

        return "redirect:/account";
    }

    @PostMapping("/repay")
    public String repayCredit(@ModelAttribute("repayCredit")
                              @Validated(View.New.class) RepayCreditDto repayCreditDto,
                              BindingResult bindingResult,
                              RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
            return "redirect:/account";
        }

        try {
            transactionService.save(TransactionDto.builder()
                    .date(new SimpleDateFormat(TransactionDto.DATE_PATTERN).format(new Date()))
                    .transactionDetails(TransactionDetailsDto.builder()
                            .transactionType(TransactionType.CREDIT)
                            .amount(repayCreditDto.getAmount())
                            .currency(repayCreditDto.getCurrency())
                            .fromAccountId(repayCreditDto.getAccountId())
                            .creditId(repayCreditDto.getCreditId())
                            .build())
                    .build());
        } catch (Exception e) {
            attributes.addFlashAttribute("errors", e.getLocalizedMessage());
            return "redirect:/account";
        }

        attributes.addFlashAttribute("success", "Success! You repaid a loan or " +
                "a part of it.");

        return "redirect:/account";
    }

}
