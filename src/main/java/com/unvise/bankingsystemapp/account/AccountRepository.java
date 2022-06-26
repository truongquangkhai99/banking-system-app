package com.unvise.bankingsystemapp.account;

import com.unvise.bankingsystemapp.common.BaseRepository;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

    List<Account> findByCurrency(CurrencyType currency);

}
