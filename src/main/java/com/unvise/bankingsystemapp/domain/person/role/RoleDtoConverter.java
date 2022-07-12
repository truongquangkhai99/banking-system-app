package com.unvise.bankingsystemapp.domain.person.role;

import com.unvise.bankingsystemapp.domain.person.web.dto.RoleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter implements Converter<String, RoleDto> {

    @Override
    public RoleDto convert(String source) {
        return new RoleDto(null, RoleType.valueOf(source));
    }

}
