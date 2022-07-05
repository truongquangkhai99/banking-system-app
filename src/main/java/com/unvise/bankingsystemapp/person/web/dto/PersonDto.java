package com.unvise.bankingsystemapp.person.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.account.web.dto.AccountDto;
import com.unvise.bankingsystemapp.common.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    @Null(groups = {View.New.class, View.Update.class})
    private Long id;

    @NotNull(groups = {View.New.class, View.Update.class})
    @Pattern(regexp = "^([a-zA-Z]{2,}\\s[a-zA-Z]+'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]+)?)")
    private String firstname;

    @NotNull(groups = {View.New.class, View.Update.class})
    @Pattern(regexp = "^([a-zA-Z]{2,}\\s[a-zA-Z]+'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]+)?)")
    private String lastname;

    @NotNull(groups = {View.New.class, View.Update.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @Email
    @NotNull(groups = {View.New.class, View.Update.class})
    private String email;

    @NotNull(groups = {View.New.class, View.Update.class})
    @Pattern(regexp = "^[+]*[(]?\\d{1,4}[)]?[-\\s./\\d]*$")
    private String phone;

    @NotNull(groups = {View.New.class, View.Update.class})
    private String password;

    @NotNull(groups = {View.New.class, View.Update.class})
    private Boolean status;

    private AccountDto account;

    private Set<RoleDto> roles;

}
