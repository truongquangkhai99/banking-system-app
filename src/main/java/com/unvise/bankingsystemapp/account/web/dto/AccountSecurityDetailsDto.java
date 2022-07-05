package com.unvise.bankingsystemapp.account.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.common.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSecurityDetailsDto {

    @Null(groups = {View.New.class, View.Update.class})
    private Long id;

    @NotNull(groups = {View.New.class, View.Update.class})
    @JsonProperty("password_hash")
    private String passwordHash;

}
