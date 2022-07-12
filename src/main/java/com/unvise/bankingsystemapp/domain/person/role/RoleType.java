package com.unvise.bankingsystemapp.domain.person.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleType {

    USER("USER"), ADMIN("ADMIN");

    @Getter
    private final String roleTypeAsString;

}
