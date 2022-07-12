package com.unvise.bankingsystemapp.domain.auth;

import com.unvise.bankingsystemapp.domain.auth.web.dto.SignInDto;
import com.unvise.bankingsystemapp.domain.auth.web.dto.SignUpDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
import com.unvise.bankingsystemapp.exception.token.JwtTokenException;

/**
 * Interface for user registration and authorization
 *
 * @author unvise
 * @version 0.1
 */
public interface AuthService {

    /**
     * Method for user authorization
     *
     * @param signInDto Dto class
     * @return JWT in string format
     * @throws JwtTokenException Throws an exception if the JWT contains incorrect user information, or if the JWT has expired
     * @throws ResourceException Throws an exception that is associated with a resource. e.g.
     * the resource is not found, the resource already exists, etc.
     * @see com.unvise.bankingsystemapp.domain.auth.jwt.JwtTokenProvider
     * @see SignInDto
     */
    String signin(SignInDto signInDto) throws JwtTokenException, ResourceException;

    /**
     * Method for user registration
     *
     * @param signUpDto Dto class
     * @return JWT in string format
     * @throws ResourceException Throws an exception that is associated with a resource. e.g.
     * the resource is not found, the resource already exists, etc.
     * @see com.unvise.bankingsystemapp.domain.auth.jwt.JwtTokenProvider
     * @see SignUpDto
     */
    String signup(SignUpDto signUpDto) throws ResourceException;

}
