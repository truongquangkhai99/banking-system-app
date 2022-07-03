package com.unvise.bankingsystemapp.transaction.details;

import com.unvise.bankingsystemapp.account.account.Account;
import com.unvise.bankingsystemapp.credit.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public class TransactionDetailsMapperHelper {

    @Named("getAccountId")
    public static Long getAccountId(Account account) {
        if (account == null) {
            return null;
        }
        return account.getId();
    }

    @Named("getCreditId")
    public static Long getCreditId(Credit credit) {
        if (credit == null) {
            return null;
        }
        return credit.getId();
    }

}
