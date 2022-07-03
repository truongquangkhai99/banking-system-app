package com.unvise.bankingsystemapp.person.person;

import com.unvise.bankingsystemapp.common.BaseService;
import com.unvise.bankingsystemapp.person.web.dto.PersonDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PersonService extends BaseService<PersonDto, Long>, UserDetailsService {

    List<PersonDto> getByFirstname(String firstname);

    List<PersonDto> getByLastname(String lastname);

    List<PersonDto> getByFirstnameAndLastname(String firstname, String lastname);

    PersonDto getByEmail(String email);

    PersonDto getByPhone(String phone);

}
