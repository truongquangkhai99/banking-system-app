package com.unvise.bankingsystemapp.domain.person.person;

import com.unvise.bankingsystemapp.domain.account.security.AccountSecurityDetails;
import com.unvise.bankingsystemapp.exception.ResourceNotFoundException;
import com.unvise.bankingsystemapp.domain.person.role.RoleRepository;
import com.unvise.bankingsystemapp.domain.person.web.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final PersonMapper personMapper;

    @Override
    @Transactional
    public List<PersonDto> getAll() {
        List<Person> foundPersons = personRepository.findAll();
        return personMapper.toDtoList(foundPersons);
    }

    @Override
    @Transactional
    public PersonDto getById(Long aLong) {
        Person foundPerson = personRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Person", Map.of("id", aLong)));
        return personMapper.toDto(foundPerson);
    }

    @Override
    @Transactional
    public PersonDto save(PersonDto personDto) {
        Person unsavedPerson = personMapper.toEntity(personDto);
        setRolesIdToPerson(unsavedPerson);

        setEncodedPasswordToPerson(unsavedPerson, personDto.getPassword());

        // set encoded password in AccountSecurityDetailsDto
        setEncodedPasswordToAccountSecurityDetails(
                unsavedPerson.getAccount().getAccountSecurityDetails(),
                personDto.getAccount().getAccountSecurityDetails().getPasswordHash()
        );

        Person savedPerson = personRepository.save(unsavedPerson);
        return personMapper.toDto(savedPerson);
    }

    @Override
    @Transactional
    public PersonDto updateById(Long aLong, PersonDto personDto) {
        personRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Person", Map.of("id", aLong)));
        personDto.setId(aLong);

        // can't be null because person exist
        Person existingPerson = personRepository.findById(aLong).get();

        Person unsavedPerson = personMapper.toEntity(personDto);

        unsavedPerson.getAccount().setId(existingPerson.getAccount().getId());
        unsavedPerson.getAccount().setAccountHistory(existingPerson.getAccount().getAccountHistory());
        unsavedPerson.getAccount().getAccountHistory().setId(existingPerson.getAccount().getAccountHistory().getId());
        unsavedPerson.getAccount().getAccountSecurityDetails().setId(existingPerson.getAccount().getAccountSecurityDetails().getId());

        setRolesIdToPerson(unsavedPerson);

        setEncodedPasswordToPerson(unsavedPerson, personDto.getPassword());

        // set encoded password in AccountSecurityDetailsDto
        setEncodedPasswordToAccountSecurityDetails(
                unsavedPerson.getAccount().getAccountSecurityDetails(),
                personDto.getAccount().getAccountSecurityDetails().getPasswordHash()
        );

        Person savedPerson = personRepository.save(unsavedPerson);
        return personMapper.toDto(savedPerson);
    }

    @Override
    @Transactional
    public PersonDto deleteById(Long aLong) {
        Person foundPerson = personRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Person", Map.of("id", aLong)));
        personRepository.delete(foundPerson);
        return personMapper.toDto(foundPerson);
    }

    @Override
    @Transactional
    public List<PersonDto> getByFirstname(String firstname) {
        List<Person> foundPersons = personRepository.findByFirstname(firstname);
        return personMapper.toDtoList(foundPersons);
    }

    @Override
    @Transactional
    public List<PersonDto> getByLastname(String lastname) {
        List<Person> foundPersons = personRepository.findByLastname(lastname);
        return personMapper.toDtoList(foundPersons);
    }

    @Override
    @Transactional
    public List<PersonDto> getByFirstnameAndLastname(String firstname, String lastname) {
        List<Person> foundPersons = personRepository.findByFirstnameAndLastname(firstname, lastname);
        return personMapper.toDtoList(foundPersons);
    }

    @Override
    @Transactional
    public PersonDto getByEmail(String email) {
        Person foundPerson = personRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Person", Map.of("email", email)));
        return personMapper.toDto(foundPerson);
    }

    @Override
    @Transactional
    public PersonDto getByPhone(String phone) {
        Person foundPerson = personRepository.findByPhone(phone).orElseThrow(() ->
                new ResourceNotFoundException("Person", Map.of("phone", phone)));
        return personMapper.toDto(foundPerson);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Person", Map.of("email", email)));

        return new PersonPrincipal(person);
    }

    private void setRolesIdToPerson(Person person) {
        person.getRoles().forEach(role ->
                // can't be null because all roles must save on application start in @StarterSavingRoles class
                role.setId(roleRepository.findByRole(role.getRole()).get().getId())
        );
    }

    private void setEncodedPasswordToPerson(Person person, String password) {
        person.setPassword(encoder.encode(password));
    }

    private void setEncodedPasswordToAccountSecurityDetails(AccountSecurityDetails accountSecurityDetails,
                                                            String password) {
        accountSecurityDetails.setPasswordHash(encoder.encode(password));
    }

}
