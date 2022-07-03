package com.unvise.bankingsystemapp.person.role;

import com.unvise.bankingsystemapp.person.web.dto.RoleDto;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

    Set<RoleDto> toDtoSet(Set<Role> roles);

}
