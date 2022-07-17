package com.unvise.bankingsystemapp.domain.account.web.mvc;

import com.unvise.bankingsystemapp.domain.common.View;
import com.unvise.bankingsystemapp.domain.account.account.AccountService;
import com.unvise.bankingsystemapp.domain.account.history.AccountHistoryService;
import com.unvise.bankingsystemapp.domain.account.web.dto.TopUpAccountBalanceDto;
import com.unvise.bankingsystemapp.domain.credit.CreditServiceImpl;
import com.unvise.bankingsystemapp.domain.credit.web.dto.CreditDto;
import com.unvise.bankingsystemapp.domain.credit.web.dto.RepayCreditDto;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.domain.deposit.web.dto.DepositDto;
import com.unvise.bankingsystemapp.domain.person.person.Person;
import com.unvise.bankingsystemapp.domain.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.domain.transaction.transaction.TransactionService;
import com.unvise.bankingsystemapp.domain.transaction.transaction.TransactionServiceImpl;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDetailsDto;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountHistoryService accountHistoryService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping
    public String account(@AuthenticationPrincipal Person userDetails, Model model) {
        Long accountHistoryId = userDetails.getAccount().getAccountHistory().getId();

        if (!model.containsAttribute("account")) {
            model.addAttribute("account", accountService.getById(userDetails.getAccount().getId()));
        }

        DepositDto depositDto = accountHistoryService.getById(accountHistoryId).getDeposit();

        if (depositDto == null) {
            model.addAttribute("deposit", new DepositDto());
        } else {
            if (!model.containsAttribute("deposit")) {
                model.addAttribute("deposit", depositDto);
            }
        }

        if (!model.containsAttribute("credit")) {
            model.addAttribute("credit", new CreditDto());
        }

        if (!model.containsAttribute("repayCredit")) {
            model.addAttribute("repayCredit", new RepayCreditDto());
        }

        if (!model.containsAttribute("allCurrencies")) {
            model.addAttribute("allCurrencies", CurrencyType.values());
        }

        List<TransactionDto> transactionDtos = TransactionServiceImpl.sortTransactionsInReverseOrder(
                accountHistoryService.getById(accountHistoryId).getTransaction()
        );

        List<CreditDto> creditDtos = CreditServiceImpl.sortCreditsInReverseOrder(
                accountHistoryService.getById(accountHistoryId).getCredits()
        );

        model.addAttribute("topUpBalance", new TopUpAccountBalanceDto());
        model.addAttribute("transactions", transactionDtos);
        model.addAttribute("credits", creditDtos);

        return "account/account-page";
    }

    @PostMapping("/top-up")
    public String topUpAccountBalance(@AuthenticationPrincipal Person userDetails,
                                      @ModelAttribute("topUpBalance")
                                      @Validated(View.New.class) TopUpAccountBalanceDto topUpAccountBalanceDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList())
            );
            return "redirect:/account";
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            TransactionDto transactionDto = TransactionDto.builder()
                    .date(formatter.format(new Date()))
                    .transactionDetails(TransactionDetailsDto.builder()
                            .amount(topUpAccountBalanceDto.getAmount())
                            .fromAccountId(userDetails.getAccount().getId())
                            .currency(topUpAccountBalanceDto.getCurrency())
                            .transactionType(TransactionType.TOP_UP_ACCOUNT_BALANCE)
                            .build())
                    .build();


            transactionService.save(transactionDto);
        } catch (Exception e) {
            attributes.addFlashAttribute("errors", List.of(e.getLocalizedMessage()));

            return "redirect:/account";
        }

        attributes.addFlashAttribute("success", "Success! " +
                "You have successfully funded your account");

        return "redirect:/account";
    }

}
