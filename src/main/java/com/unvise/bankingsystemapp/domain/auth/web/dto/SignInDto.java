package com.unvise.bankingsystemapp.domain.auth.web.dto;

import com.unvise.bankingsystemapp.common.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    @NotNull(message = "email must not be null", groups = View.New.class)
    @Email(message = "invalid pattern", groups = View.New.class)
    private String email;

    @NotNull(message = "password must not be null", groups = View.New.class)
    @Size(message = "password length must be in range [5, 100]", min = 5, max = 100, groups = View.New.class)
    private String password;

}
