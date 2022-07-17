package com.unvise.bankingsystemapp.domain.auth.web.rest;

import com.unvise.bankingsystemapp.domain.auth.AuthService;
import com.unvise.bankingsystemapp.domain.auth.web.dto.SignInDto;
import com.unvise.bankingsystemapp.domain.auth.web.dto.SignUpDto;
import com.unvise.bankingsystemapp.domain.common.View;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @ResponseBody
    @PostMapping("/jwt/signin")
    public ResponseEntity<?> signInJwt(@RequestBody @Validated(View.New.class) SignInDto signInDto) {
        return new ResponseEntity<>(authService.signin(signInDto), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/jwt/signup")
    public ResponseEntity<?> signUpJwt(@RequestBody @Validated(View.New.class) SignUpDto signUpDto) {
        return new ResponseEntity<>(authService.signup(signUpDto), HttpStatus.OK);
    }

}
