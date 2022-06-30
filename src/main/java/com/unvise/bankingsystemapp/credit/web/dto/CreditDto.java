package com.unvise.bankingsystemapp.credit.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.credit.web.validator.ValidateCredit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidateCredit(groups = {View.Update.class})
public class CreditDto {

    @Null(groups = {View.New.class, View.Update.class})
    @JsonView(View.Details.class)
    private Long id;

    @NotNull(groups = {View.New.class})
    @Positive(groups = {View.New.class})
    @JsonView(View.Details.class)
    private BigDecimal total;

    @Null(groups = {View.New.class})
    @Positive(groups = {View.Update.class})
    @JsonView(View.Details.class)
    private BigDecimal current;

    @Null(groups = {View.New.class})
    @PositiveOrZero(groups = {View.Update.class})
    @JsonView(View.Details.class)
    private BigDecimal remain;

    @NotNull(groups = {View.New.class})
    @JsonView(View.Details.class)
    private Integer dateBetweenPaymentsInDays;

    @Null(groups = {View.New.class})
    @NotNull(groups = {View.Update.class})
    @JsonView(View.Details.class)
    private Boolean isClosed;

    @NotNull(groups = {View.New.class, View.Update.class})
    private Long accountHistoryId;

}
