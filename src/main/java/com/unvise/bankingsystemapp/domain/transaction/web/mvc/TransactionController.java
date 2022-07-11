package com.unvise.bankingsystemapp.domain.transaction.web.mvc;

import com.unvise.bankingsystemapp.domain.common.View;
import com.unvise.bankingsystemapp.domain.person.person.Person;
import com.unvise.bankingsystemapp.domain.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.domain.transaction.transaction.TransactionService;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDetailsDto;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import com.unvise.bankingsystemapp.domain.transaction.web.validator.TransactionDetailsCustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionDetailsCustomValidator transactionDetailsCustomValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(transactionDetailsCustomValidator);
    }

    @GetMapping
    public String showTransactionPage(@AuthenticationPrincipal Person userDetails,
                                      Model model) {

        model.addAttribute("transactionDetails", TransactionDetailsDto.builder()
                .fromAccountId(userDetails.getAccount().getId())
                .build());

        model.addAttribute("transactionTypes", TransactionType.values());

        return "transaction/transaction-page";
    }

    @PostMapping
    private String manageTransaction(@AuthenticationPrincipal Person userDetails,
                                     @ModelAttribute("transactionDetails")
                                     @Validated(View.New.class) TransactionDetailsDto transactionDetailsDto,
                                     BindingResult bindingResult,
                                     RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
            return "redirect:/transaction";
        }

        transactionDetailsDto.setFromAccountId(userDetails.getAccount().getId());

        TransactionDto transactionDto = TransactionDto.builder()
                .date(new SimpleDateFormat(TransactionDto.DATE_PATTERN).format(new Date()))
                .transactionDetails(transactionDetailsDto)
                .build();

        try {
            transactionService.save(transactionDto);
        } catch (Exception e) {
            attributes.addFlashAttribute("errors", List.of(e.getLocalizedMessage()));
            return "redirect:/transaction";
        }

        attributes.addFlashAttribute("success", "Success! The transaction was completed successfully.");

        return "redirect:/transaction";
    }

}
