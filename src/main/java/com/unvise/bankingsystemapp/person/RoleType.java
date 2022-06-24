package com.unvise.bankingsystemapp.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleType {

    REGULAR_USER("REGULAR_USER"), EMPLOYEE("EMPLOYEE"), ADMIN("ADMIN");

    @Getter
    private final String roleTypeAsString;

}
