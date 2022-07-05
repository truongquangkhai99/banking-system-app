package com.unvise.bankingsystemapp.domain.account.account;

import com.unvise.bankingsystemapp.domain.account.web.dto.AccountDto;
import com.unvise.bankingsystemapp.common.BaseService;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;

import java.util.List;

public interface AccountService extends BaseService<AccountDto, Long> {

    List<AccountDto> getByCurrency(CurrencyType currency);
    
}
