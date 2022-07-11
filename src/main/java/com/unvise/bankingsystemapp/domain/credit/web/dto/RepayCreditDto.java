package com.unvise.bankingsystemapp.domain.credit.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepayCreditDto {

    @NotNull(message = "Amount must not be null", groups = View.New.class)
    @DecimalMin(value = "1", message = "Credit repay amount must be greater or equals 1", groups = View.New.class)
    private BigDecimal amount;

    @NotNull(message = "currency must not be null", groups = View.New.class)
    private CurrencyType currency;


    @NotNull(message = "account id id id must not be null", groups = View.New.class)
    @Positive(message = "account id id must be greater than zero", groups = View.New.class)
    @JsonProperty("account_id")
    private Long accountId;

//    @NotNull(message = "account history id id must not be null", groups = View.New.class)
//    @Positive(message = "account history id must be greater than zero", groups = View.New.class)
//    @JsonProperty("account_history_id")
//    private Long accountHistoryId;

    @NotNull(message = "credit id must not be null", groups = View.New.class)
    @Positive(message = "credit id must be greater than zero", groups = View.New.class)
    @JsonProperty("credit_id")
    private Long creditId;

}
