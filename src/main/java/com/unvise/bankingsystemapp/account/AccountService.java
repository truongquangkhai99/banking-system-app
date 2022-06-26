package com.unvise.bankingsystemapp.account;

import com.unvise.bankingsystemapp.account.web.dto.AccountDto;
import com.unvise.bankingsystemapp.common.BaseService;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService extends BaseService<AccountDto, Long> {

    List<AccountDto> getByCurrency(CurrencyType currency);
    
}
