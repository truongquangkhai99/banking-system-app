package com.unvise.bankingsystemapp.account.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSecurityDetailsDto {

    @Null
    private Long id;

    private String passwordHash;

}
