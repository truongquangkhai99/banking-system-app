package com.unvise.bankingsystemapp.domain.auth.web.dto;

import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.auth.web.validator.MatchedField;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@MatchedField(
        first = "password",
        second = "confirmedPassword",
        groups = View.New.class
)
public class SignUpDto {

    @NotEmpty(message = "firstname must not be empty", groups = View.New.class)
    private String firstname;

    @NotEmpty(message = "lastname must not be empty", groups = View.New.class)
    private String lastname;

    @NotEmpty(message = "email must not be empty", groups = View.New.class)
    @Email(message = "invalid pattern", groups = View.New.class)
    private String email;

    @NotEmpty(message = "phone must not be empty", groups = View.New.class)
    @Pattern(message = "invalid pattern Ex. +7 (952) 951-23-21",
            regexp = "^(\\+7|7|8)?[\\s\\-]?\\(?[489]\\d{2}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$",
            groups = {View.New.class, View.Update.class}
    )
    private String phone;

    @NotNull(message = "dateOfBirth must not be null", groups = View.New.class)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private String dateOfBirth;

    @NotEmpty(message = "password must not be empty", groups = View.New.class)
    @Size(message = "must be in range [5, 100]", min = 5, max = 100, groups = View.New.class)
    private String password;

    @NotEmpty(message = "confirmed password must not be empty", groups = View.New.class)
    @Size(message = "confirmed password must be in range [5, 100]", min = 5, max = 100, groups = View.New.class)
    private String confirmedPassword;

    @NotNull(message = "currency must not be null", groups = View.New.class)
    private CurrencyType currency;

    @NotEmpty(message = "password must not be empty", groups = View.New.class)
    @Size(message = "must be in range [4, 20]", min = 4, max = 20, groups = View.New.class)
    private String accountPassword;

}
