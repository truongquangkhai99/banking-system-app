package com.unvise.bankingsystemapp.domain.admin.web.mvc;

import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.account.account.AccountService;
import com.unvise.bankingsystemapp.domain.account.web.dto.AccountDto;
import com.unvise.bankingsystemapp.domain.credit.CreditService;
import com.unvise.bankingsystemapp.domain.credit.web.dto.CreditDto;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.domain.deposit.DepositService;
import com.unvise.bankingsystemapp.domain.deposit.web.dto.DepositDto;
import com.unvise.bankingsystemapp.domain.person.person.PersonService;
import com.unvise.bankingsystemapp.domain.person.role.RoleType;
import com.unvise.bankingsystemapp.domain.person.web.dto.PersonDto;
import com.unvise.bankingsystemapp.domain.transaction.transaction.TransactionService;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PersonService personService;
    private final AccountService accountService;
    private final CreditService creditService;
    private final DepositService depositService;
    private final TransactionService transactionService;

    @GetMapping("/person")
    public String persons(Model model) {
        List<PersonDto> persons = personService.getAll();
        persons.sort(Comparator.comparing(PersonDto::getId));

        model.addAttribute("persons", persons);

        return "admin/person";
    }

    @GetMapping("/person/{id}/edit")
    public String showPersonEditForm(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("person")) {
            model.addAttribute("person", personService.getById(id));
        }

        if (!model.containsAttribute("allRoles")) {
            model.addAttribute("allRoles", RoleType.values());
        }

        return "admin/edit-person";
    }

    @PostMapping("/person/{id}/update")
    public String personEdit(@PathVariable("id") Long personId,
                             @ModelAttribute("person") @Validated(View.Update.class) PersonDto personDtoForUpdate,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attributes) {

        List<FieldError> errorsToKeep = bindingResult.getFieldErrors().stream()
                .filter(fer -> !fer.getField().equals("account")
                        && !fer.getField().equals("id")
                )
                .collect(Collectors.toList());

        if (!errorsToKeep.isEmpty()) {
            model.addAttribute("person", personDtoForUpdate);
            return "admin/edit-person";
        }

        PersonDto personDto = personService.getById(personId);

        personDtoForUpdate.setAccount(personDto.getAccount());
        personDtoForUpdate.getAccount().getAccountSecurityDetails().setPasswordHash(
                personDto.getAccount().getAccountSecurityDetails().getPasswordHash()
        );

        try {
            personService.updateById(personDtoForUpdate.getId(), personDtoForUpdate);
        } catch (Exception e) {
            model.addAttribute("exceptionMessage", e.getLocalizedMessage());
            return "admin/edit-person";
        }

        attributes.addFlashAttribute("success", "Success! Person was successfully updated.");

        return "redirect:/admin/person";
    }

    @PostMapping("/person/{id}/delete")
    public String personDelete(@PathVariable("id") Long personId, RedirectAttributes attributes) {
        personService.deleteById(personId);

        attributes.addFlashAttribute("success", "Success! Person was successfully deleted.");

        return "redirect:/admin/person";
    }


    @GetMapping("/account")
    public String accounts(Model model) {
        List<AccountDto> accounts = accountService.getAll();
        accounts.sort((Comparator.comparing(AccountDto::getId)));

        model.addAttribute("accounts", accounts);

        return "admin/account";
    }

    @GetMapping("/account/{id}/edit")
    public String showAccountEditForm(@PathVariable("id") Long accountId, Model model) {
        if (!model.containsAttribute("account")) {
            model.addAttribute("account", accountService.getById(accountId));
        }

        if (!model.containsAttribute("allCurrencies")) {
            model.addAttribute("allCurrencies", CurrencyType.values());
        }

        return "admin/edit-account";
    }

    @PostMapping("/account/{id}/update")
    public String accountEdit(@PathVariable("id") Long accountId,
                              @ModelAttribute("account") @Validated(View.Update.class) AccountDto accountDtoForUpdate,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attributes) {

        List<FieldError> errorsToKeep = bindingResult.getFieldErrors().stream()
                .filter(fer -> !fer.getField().equals("id"))
                .collect(Collectors.toList());

        if (!errorsToKeep.isEmpty()) {
            model.addAttribute("account", accountDtoForUpdate);
            return "admin/edit-account";
        }

        accountService.updateById(accountId, accountDtoForUpdate);

        attributes.addFlashAttribute("success", "Success! Account was successfully updated!");

        return "redirect:/admin/account";
    }

    @PostMapping("/account/{id}/delete")
    public String accountDelete(@PathVariable("id") Long accountId, RedirectAttributes attributes) {
        AccountDto accountDto = accountService.getById(accountId);
        personService.deleteById(accountDto.getPersonId());

        attributes.addFlashAttribute("success", "Success! Account was successfully deleted.");

        return "redirect:/admin/account";
    }

    @GetMapping("/credit")
    public String credits(Model model) {
        List<CreditDto> credits = creditService.getAll();
        credits.sort(Comparator.comparing(CreditDto::getId));

        model.addAttribute("credits", credits);

        return "admin/credit";
    }

    @GetMapping("/credit/{id}/edit")
    public String showCreditEditForm(@PathVariable("id") Long creditId, Model model) {
        if (!model.containsAttribute("credit")) {
            model.addAttribute("credit", creditService.getById(creditId));
        }

        if (!model.containsAttribute("allCurrencies")) {
            model.addAttribute("allCurrencies", CurrencyType.values());
        }

        return "admin/edit-credit";
    }

    @PostMapping("/credit/{id}/update")
    public String creditUpdate(@PathVariable("id") Long creditId,
                               @ModelAttribute("credit") @Validated(View.Update.class) CreditDto creditDtoForUpdate,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes attributes) {

        List<FieldError> errorsToKeep = bindingResult.getFieldErrors().stream()
                .filter(fer -> !fer.getField().equals("accountHistoryId")
                        && !fer.getField().equals("id"))
                .collect(Collectors.toList());

        for (ObjectError o : bindingResult.getAllErrors()) {
            if (o != null && !(o instanceof FieldError)) {
                model.addAttribute("objectError", o.getDefaultMessage());
            }
        }

        if (!errorsToKeep.isEmpty() || model.containsAttribute("objectError")) {
            model.addAttribute("credit", creditDtoForUpdate);
            return "admin/edit-credit";
        }

        CreditDto creditDto = creditService.getById(creditId);
        creditDtoForUpdate.setAccountHistoryId(creditDto.getAccountHistoryId());

        creditService.updateById(creditId, creditDtoForUpdate);

        attributes.addFlashAttribute("success", "Success! Credit was successfully updated!");

        return "redirect:/admin/credit";
    }

    @PostMapping("/credit/{id}/delete")
    public String creditDelete(@PathVariable("id") Long creditId, RedirectAttributes attributes) {
        creditService.deleteById(creditId);

        attributes.addFlashAttribute("success", "Success! Credit was successfully deleted.");

        return "redirect:/admin/credit";
    }

    @GetMapping("/deposit")
    public String deposits(Model model) {
        List<DepositDto> deposits = depositService.getAll();
        deposits.sort(Comparator.comparing(DepositDto::getId));

        model.addAttribute("deposits", deposits);

        return "admin/deposit";
    }

    @GetMapping("/deposit/{id}/edit")
    public String showDepositEditForm(@PathVariable("id") Long depositId, Model model) {
        if (!model.containsAttribute("deposit")) {
            model.addAttribute("deposit", depositService.getById(depositId));
        }

        if (!model.containsAttribute("allCurrencies")) {
            model.addAttribute("allCurrencies", CurrencyType.values());
        }

        return "admin/edit-deposit";
    }

    @PostMapping("/deposit/{id}/update")
    public String depositUpdate(@PathVariable("id") Long depositId,
                                @ModelAttribute("credit") @Validated(View.Update.class) DepositDto depositDtoForUpdate,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes attributes) {

        List<FieldError> errorsToKeep = bindingResult.getFieldErrors().stream()
                .filter(fer -> !fer.getField().equals("accountHistoryId")
                        && !fer.getField().equals("id"))
                .collect(Collectors.toList());

        if (!errorsToKeep.isEmpty()) {
            model.addAttribute("deposit", depositDtoForUpdate);
            return "admin/edit-deposit";
        }

        DepositDto depositDto = depositService.getById(depositId);
        depositDtoForUpdate.setAccountHistoryId(depositDto.getAccountHistoryId());

        depositService.updateById(depositId, depositDtoForUpdate);

        attributes.addFlashAttribute("success", "Success! Deposit was successfully updated!");

        return "redirect:/admin/deposit";
    }

    @PostMapping("/deposit/{id}/delete")
    public String depositDelete(@PathVariable("id") Long depositId, RedirectAttributes attributes) {
        depositService.deleteById(depositId);

        attributes.addFlashAttribute("success", "Success! Deposit was successfully deleted.");

        return "redirect:/admin/deposit";
    }


    @GetMapping("/transaction")
    public String transactions(Model model) {
        List<TransactionDto> transactions = transactionService.getAll();
        transactions.sort(Comparator.comparing(TransactionDto::getId));

        model.addAttribute("transactions", transactions);

        return "admin/transaction";
    }

    @PostMapping("/transaction/{id}/delete")
    public String transactionDelete(@PathVariable("id") Long transactionId, RedirectAttributes attributes) {
        transactionService.deleteById(transactionId);

        attributes.addFlashAttribute("success", "Success! Transaction was successfully deleted.");

        return "redirect:/admin/transaction";
    }

}
