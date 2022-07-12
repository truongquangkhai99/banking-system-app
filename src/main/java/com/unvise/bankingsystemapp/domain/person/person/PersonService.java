package com.unvise.bankingsystemapp.domain.person.person;

import com.unvise.bankingsystemapp.domain.common.BaseService;
import com.unvise.bankingsystemapp.domain.person.web.dto.PersonDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PersonService extends BaseService<PersonDto, Long>, UserDetailsService {

    List<PersonDto> getByFirstname(String firstname);

    List<PersonDto> getByLastname(String lastname);

    List<PersonDto> getByFirstnameAndLastname(String firstname, String lastname);

    PersonDto getByEmail(String email) throws ResourceNotFoundException;

    PersonDto getByPhone(String phone) throws ResourceNotFoundException;

}
