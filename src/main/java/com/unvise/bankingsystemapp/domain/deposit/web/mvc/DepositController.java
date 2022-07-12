package com.unvise.bankingsystemapp.domain.deposit.web.mvc;

import com.unvise.bankingsystemapp.domain.common.View;
import com.unvise.bankingsystemapp.domain.account.history.AccountHistoryService;
import com.unvise.bankingsystemapp.domain.currency.util.CurrencyConverter;
import com.unvise.bankingsystemapp.domain.deposit.DepositService;
import com.unvise.bankingsystemapp.domain.deposit.web.dto.DepositDto;
import com.unvise.bankingsystemapp.domain.person.person.Person;
import com.unvise.bankingsystemapp.domain.person.person.PersonService;
import com.unvise.bankingsystemapp.domain.person.web.dto.PersonDto;
import com.unvise.bankingsystemapp.domain.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.domain.transaction.transaction.TransactionService;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDetailsDto;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/deposit")
@RequiredArgsConstructor
public class DepositController {

    private final PersonService personService;
    private final AccountHistoryService accountHistoryService;
    private final DepositService depositService;
    private final CurrencyConverter currencyConverter;
    private final TransactionService transactionService;

    @PostMapping
    private String openDeposit(@AuthenticationPrincipal Person userDetails,
                               @ModelAttribute("deposit")
                               @Validated(View.New.class) DepositDto depositDto,
                               BindingResult bindingResult,
                               RedirectAttributes attributes) {

        List<FieldError> errorsToKeep = bindingResult.getFieldErrors().stream()
                .filter(fer -> !fer.getField().equals("accountHistoryId"))
                .collect(Collectors.toList());

        if (!errorsToKeep.isEmpty()) {
            attributes.addFlashAttribute("errors", errorsToKeep.stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
            return "redirect:/account";
        }

        depositDto.setAccountHistoryId(userDetails.getAccount().getAccountHistory().getId());

        depositService.save(depositDto);

        attributes.addFlashAttribute("success", "Success! You have opened a deposit account");

        return "redirect:/account";
    }

    @PostMapping("/withdraw")
    private String withdrawDeposit(@AuthenticationPrincipal Person userDetails,
                                   RedirectAttributes attributes) {
        DepositDto depositDto =  accountHistoryService
                .getById(userDetails.getAccount().getAccountHistory().getId())
                .getDeposit();

        if (depositDto.getBalance().compareTo(BigDecimal.ZERO) < 1) {
            attributes.addFlashAttribute("errors", List.of("Your deposit balance is empty."));
            return "redirect:/account";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        TransactionDto transactionDto = TransactionDto.builder()
                .date(formatter.format(new Date()))
                .transactionDetails(TransactionDetailsDto.builder()
                        .amount(depositDto.getBalance())
                        .fromAccountId(userDetails.getAccount().getId())
                        .transactionType(TransactionType.WITHDRAW_DEPOSIT)
                        .build())
                .build();

        try {
            transactionService.save(transactionDto);
        } catch (ResourceNotFoundException e) {
            attributes.addFlashAttribute("errors", List.of(new StringBuilder()
                    .append("Can't convert from currency: ")
                    .append(e.getFieldsAndValues().get("fromCurrency"))
                    .append(" to currency: ")
                    .append(e.getFieldsAndValues().get("toCurrency"))
                    .append(". This exchange rate isn't exist")));

            return "redirect:/account";
        }

        attributes.addAttribute("success", "Success! The deposit balance has been " +
                "transferred to your account balance.");

        return "redirect:/account";
    }

    @PostMapping("/close")
    private String closeDeposit(@AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes attributes) {

        PersonDto personDto = personService.getByEmail(userDetails.getUsername());

        DepositDto depositDto =  accountHistoryService
                .getById(personDto.getAccount().getAccountHistoryId())
                .getDeposit();

        if (depositDto.getBalance().compareTo(BigDecimal.ZERO) < 1) {
            depositService.deleteById(depositDto.getId());
            attributes.addFlashAttribute("success", "Success! Your deposit was " +
                    "successfully deleted!");

            return "redirect:/account";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        TransactionDto transactionDto = TransactionDto.builder()
                .date(formatter.format(new Date()))
                .transactionDetails(TransactionDetailsDto.builder()
                        .amount(depositDto.getBalance())
                        .fromAccountId(personDto.getAccount().getId())
                        .transactionType(TransactionType.WITHDRAW_DEPOSIT)
                        .build())
                .build();

        try {
            transactionService.save(transactionDto);
        } catch (ResourceNotFoundException e) {
            attributes.addFlashAttribute("errors", List.of(new StringBuilder()
                    .append("Can't convert from currency: ")
                    .append(e.getFieldsAndValues().get("fromCurrency"))
                    .append(" to currency: ")
                    .append(e.getFieldsAndValues().get("toCurrency"))
                    .append(". This exchange rate isn't exist")));

            return "redirect:/account";
        }

        depositService.deleteById(depositDto.getId());

        attributes.addAttribute("success", "Success! When closing the deposit, its " +
                "balance was automatically transferred to your account.");

        return "redirect:/account";
    }

}
