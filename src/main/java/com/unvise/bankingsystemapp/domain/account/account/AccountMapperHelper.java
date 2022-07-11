package com.unvise.bankingsystemapp.domain.account.account;

import com.unvise.bankingsystemapp.domain.account.history.AccountHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public class AccountMapperHelper {

    @Named("accountHistoryId")
    public static Long getAccountHistoryId(AccountHistory accountHistory) {
        if (accountHistory == null) {
            return null;
        }
        return accountHistory.getId();
    }

    @Named("personId")
    public static Long getPersonId(Account account) {
        if (account.getPerson() == null) {
            return null;
        }
        return account.getPerson().getId();
    }

}
