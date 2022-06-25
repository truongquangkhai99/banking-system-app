package com.unvise.bankingsystemapp.role.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleType {

    USER("USER"), ADMIN("ADMIN");

    @Getter
    private final String roleTypeAsString;

}
