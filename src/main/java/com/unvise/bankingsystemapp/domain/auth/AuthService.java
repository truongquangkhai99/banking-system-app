package com.unvise.bankingsystemapp.domain.auth;

import com.unvise.bankingsystemapp.domain.auth.web.dto.SignInDto;
import com.unvise.bankingsystemapp.domain.auth.web.dto.SignUpDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
import com.unvise.bankingsystemapp.exception.token.JwtTokenException;

public interface AuthService {

    String signin(SignInDto signInDto) throws JwtTokenException, ResourceException;

    String signup(SignUpDto signUpDto) throws ResourceException;

}
