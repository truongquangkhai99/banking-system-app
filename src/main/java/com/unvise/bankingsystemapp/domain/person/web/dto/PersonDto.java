package com.unvise.bankingsystemapp.domain.person.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.account.web.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    @Null(message = "id must be null", groups = {View.New.class, View.Update.class})
    private Long id;

    @NotEmpty(message = "firstname must be not null or empty", groups = {View.New.class, View.Update.class})
    @Size(min = 2, max = 70, message = "firstname length must be in range [2, 70]", groups = {View.New.class, View.Update.class})
    private String firstname;

    @NotEmpty(message = "lastname must be not null or empty", groups = {View.New.class, View.Update.class})
    @Size(min = 2, max = 70, message = "lastname length must be in range [2, 70]", groups = {View.New.class, View.Update.class})
    private String lastname;

    @NotNull(message = "date_of_birth must be not null and have format 'yyyy-MM-dd'", groups = {View.New.class, View.Update.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @NotNull(message = "email must be not null", groups = {View.New.class, View.Update.class})
    @Email(message = "invalid pattern", groups = {View.New.class, View.Update.class})
    @Size(min = 8, max = 100, message = "email length must be in range [5, 100]", groups = {View.New.class, View.Update.class})
    private String email;

    @NotNull(message = "phone must be not null", groups = {View.New.class, View.Update.class})
    @Pattern(message = "invalid pattern", regexp = "^(\\+7|7|8)?[\\s\\-]?\\(?[489]\\d{2}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$", groups = {View.New.class, View.Update.class})
    private String phone;

    @NotNull(message = "password must be not null", groups = {View.New.class, View.Update.class})
    @Size(min = 5, max = 100, message = "password length must be in range [5, 100]", groups = {View.New.class, View.Update.class})
    private String password;

    @NotNull(message = "status must be not null", groups = {View.New.class, View.Update.class})
    private Boolean status;

    @NotNull(message = "account must be not null with json object with fields inside", groups = {View.New.class, View.Update.class})
    private AccountDto account;

    @NotNull(message = "roles must be not null with json object with fields inside", groups = {View.New.class, View.Update.class})
    private Set<RoleDto> roles;

}
