package com.unvise.bankingsystemapp.domain.auth;

import com.unvise.bankingsystemapp.domain.auth.jwt.JwtTokenProvider;
import com.unvise.bankingsystemapp.domain.auth.web.dto.SignInDto;
import com.unvise.bankingsystemapp.domain.auth.web.dto.SignUpDto;
import com.unvise.bankingsystemapp.domain.person.person.PersonService;
import com.unvise.bankingsystemapp.domain.person.role.RoleType;
import com.unvise.bankingsystemapp.domain.person.web.dto.PersonDto;
import com.unvise.bankingsystemapp.domain.person.web.dto.RoleDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
import com.unvise.bankingsystemapp.exception.token.JwtTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PersonService personService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;

    @Override
    public String signin(SignInDto signInDto) throws JwtTokenException, ResourceNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword()));
            Set<RoleType> roleTypeSet = personService.getByEmail(signInDto.getEmail()).getRoles().stream()
                    .map(RoleDto::getRole)
                    .collect(Collectors.toSet());

            return jwtTokenProvider.createToken(signInDto.getEmail(), roleTypeSet);
        } catch (AuthenticationException e) {
            throw new JwtTokenException(e.getLocalizedMessage());
        }
    }

    @Override
    public String signup(SignUpDto signUpDto) throws ResourceException {
        PersonDto personDto = authMapper.toPersonDto(signUpDto);

        personService.save(personDto);

        return jwtTokenProvider.createToken(
                personDto.getEmail(),
                personDto.getRoles().stream()
                        .map(RoleDto::getRole)
                        .collect(Collectors.toSet())
        );

    }

}
