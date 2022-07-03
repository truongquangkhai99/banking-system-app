package com.unvise.bankingsystemapp.person.person;

import com.unvise.bankingsystemapp.account.account.AccountMapper;
import com.unvise.bankingsystemapp.person.web.dto.PersonDto;
import com.unvise.bankingsystemapp.person.role.RoleMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {AccountMapper.class, RoleMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {

    PersonDto toDto(Person person);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    Person toEntity(PersonDto personDto);

    List<PersonDto> toDtoList(List<Person> persons);

}
