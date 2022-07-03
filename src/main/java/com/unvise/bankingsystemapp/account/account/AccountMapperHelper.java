package com.unvise.bankingsystemapp.account.account;

import com.unvise.bankingsystemapp.account.history.AccountHistory;
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

}
