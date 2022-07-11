package com.unvise.bankingsystemapp.domain.auth.web.mvc;

import com.unvise.bankingsystemapp.domain.common.View;
import com.unvise.bankingsystemapp.domain.auth.AuthServiceImpl;
import com.unvise.bankingsystemapp.domain.auth.web.dto.SignInDto;
import com.unvise.bankingsystemapp.domain.auth.web.dto.SignUpDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @GetMapping("/signin")
    public String showSignInForm(Model model) {

        if (!model.containsAttribute("signin")) {
            model.addAttribute("signin", new SignInDto());
        }

        return "auth/signin-page";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {

        if (!model.containsAttribute("signup")) {
            model.addAttribute("signup", new SignUpDto());
        }

        return "auth/signup-page";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("signup") @Validated(View.New.class) SignUpDto signUpDto,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("signup", signUpDto);
            return "auth/signup-page";
        }

        try {
            authService.signup(signUpDto);
        } catch (ResourceAlreadyExists e) {
            model.addAttribute("exceptionMessage", e.getLocalizedMessage());
            return "auth/signup-page";
        }

        return "redirect:/signin";
    }

    @ResponseBody
    @PostMapping("/auth/jwt/signin")
    public ResponseEntity<?> signInJwt(@RequestBody @Validated(View.New.class) SignInDto signInDto) {
        return new ResponseEntity<>(authService.signin(signInDto), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/auth/jwt/signup")
    public ResponseEntity<?> signUpJwt(@RequestBody @Validated(View.New.class) SignUpDto signUpDto) {
        return new ResponseEntity<>(authService.signup(signUpDto), HttpStatus.OK);
    }
}
