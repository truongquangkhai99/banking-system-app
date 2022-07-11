package com.unvise.bankingsystemapp.domain.account.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    @Null(message = "id must be null", groups = {View.New.class, View.Update.class})
    private Long id;

    @Null(message = "while creating new account, balance must be null", groups = {View.New.class})
    @NotNull(message = "while updating account, balance must not be null", groups = {View.Update.class})
    private BigDecimal balance;

    @NotNull(message = "currency must not be null", groups = {View.New.class, View.Update.class})
    private CurrencyType currency;

    @NotNull(message = "account_security_details must not be null with json object with fields inside", groups = {View.New.class, View.Update.class})
    @JsonProperty("account_security_details")
    private AccountSecurityDetailsDto accountSecurityDetails;

    @Null(message = "while creating new account, account_history_id must be null",groups = {View.New.class})
    @NotNull(message = "while updating account, account_history_id must not be null", groups = {View.Update.class})
    @Positive(message = "while updating account, account_history_id must be positive", groups = {View.Update.class})
    @JsonProperty("account_history_id")
    private Long accountHistoryId;

    @NotNull(message = "person id must not be null", groups = {View.Update.class})
    @Positive(message = "person id must be positive", groups = {View.Update.class})
    @JsonProperty("person_id")
    private Long personId;

}
