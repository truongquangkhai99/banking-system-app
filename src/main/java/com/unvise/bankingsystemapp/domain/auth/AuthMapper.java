package com.unvise.bankingsystemapp.domain.auth;

import com.unvise.bankingsystemapp.domain.auth.web.dto.SignUpDto;
import com.unvise.bankingsystemapp.domain.person.role.RoleType;
import com.unvise.bankingsystemapp.domain.person.web.dto.PersonDto;
import com.unvise.bankingsystemapp.domain.person.web.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", imports = {Set.class, RoleDto.class, RoleType.class})
public interface AuthMapper {

    @Mapping(source = "accountPassword", target = "account.accountSecurityDetails.passwordHash")
    @Mapping(source = "currency", target = "account.currency")
    @Mapping(target = "roles", expression = "java(Set.of(new RoleDto(null, RoleType.USER)))")
    @Mapping(target = "status", expression = "java(true)")
    PersonDto toPersonDto(SignUpDto signUpDto);

}
