package com.unvise.bankingsystemapp.domain.person.person;

import com.unvise.bankingsystemapp.domain.account.security.AccountSecurityDetails;
import com.unvise.bankingsystemapp.domain.person.role.RoleRepository;
import com.unvise.bankingsystemapp.domain.person.web.dto.PersonDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
import com.unvise.bankingsystemapp.exception.resource.ResourceAlreadyExists;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
    public PersonDto getById(Long aLong) throws ResourceNotFoundException {
        Person foundPerson = personRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find person with id: " + aLong);

            e.setResourceName("Person");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        return personMapper.toDto(foundPerson);
    }

    @Override
    @Transactional
    public PersonDto save(PersonDto personDto) throws ResourceAlreadyExists {

        if (personRepository.findByEmail(personDto.getEmail()).isPresent()) {
            ResourceException e = new ResourceAlreadyExists("Person with given email already exist");

            e.setFieldsAndValues(Map.of("email", personDto.getEmail()));

            throw e;

        }

        if  (personRepository.findByPhone(personDto.getPhone()).isPresent()) {
            ResourceException e = new ResourceAlreadyExists("Person with given phone already exist");

            e.setFieldsAndValues(Map.of("phone", personDto.getPhone()));

            throw e;
        }

        Person unsavedPerson = personMapper.toEntity(personDto);

        setRolesIdToPerson(unsavedPerson);

        setEncodedPasswordToPerson(unsavedPerson, personDto.getPassword());

        // set encoded password in AccountSecurityDetails
        setEncodedPasswordToAccountSecurityDetails(
                unsavedPerson.getAccount().getAccountSecurityDetails(),
                personDto.getAccount().getAccountSecurityDetails().getPasswordHash()
        );

        Person savedPerson = personRepository.save(unsavedPerson);
        return personMapper.toDto(savedPerson);
    }

    @Override
    @Transactional
    public PersonDto updateById(Long aLong, PersonDto personDto)
            throws ResourceNotFoundException, ResourceAlreadyExists {

        personRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find person with id: " + aLong);

            e.setResourceName("Person");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        personDto.setId(aLong);

        Optional<Person> personWithSameEmail = personRepository.findByEmail(personDto.getEmail());
        Optional<Person> personWithSamePhone = personRepository.findByPhone(personDto.getPhone());

        if (personWithSameEmail.isPresent()) {
            if (!personWithSameEmail.get().getId().equals(personDto.getId())) {
                ResourceException e = new ResourceAlreadyExists("Person with given email already exists");

                e.setFieldsAndValues(Map.of("email", personDto.getEmail()));

                throw e;
            }
        }

        if (personWithSamePhone.isPresent()) {
            if (!personWithSamePhone.get().getId().equals(personDto.getId())) {
                ResourceException e = new ResourceAlreadyExists("Person with given phone already exists");

                e.setFieldsAndValues(Map.of("phone", personDto.getPhone()));

                throw e;
            }
        }

        // can't be null because person exist
        Person existingPerson = personRepository.findById(aLong).get();

        Person unsavedPerson = personMapper.toEntity(personDto);

        unsavedPerson.getAccount().setId(existingPerson.getAccount().getId());
        unsavedPerson.getAccount().setAccountHistory(existingPerson.getAccount().getAccountHistory());
        unsavedPerson.getAccount().getAccountHistory().setId(existingPerson.getAccount().getAccountHistory().getId());
        unsavedPerson.getAccount().getAccountSecurityDetails().setId(existingPerson.getAccount().getAccountSecurityDetails().getId());

        setRolesIdToPerson(unsavedPerson);

        setEncodedPasswordToPerson(unsavedPerson, personDto.getPassword());

        // set encoded password in AccountSecurityDetails
        setEncodedPasswordToAccountSecurityDetails(
                unsavedPerson.getAccount().getAccountSecurityDetails(),
                personDto.getAccount().getAccountSecurityDetails().getPasswordHash()
        );

        Person savedPerson = personRepository.save(unsavedPerson);
        return personMapper.toDto(savedPerson);
    }

    @Override
    @Transactional
    public PersonDto deleteById(Long aLong) throws ResourceNotFoundException{
        Person foundPerson = personRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find person with id: " + aLong);

            e.setResourceName("Person");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

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
    public PersonDto getByEmail(String email) throws ResourceNotFoundException{
        Person foundPerson = personRepository.findByEmail(email).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find person with email: " + email);

            e.setResourceName("Person");
            e.setFieldsAndValues(Map.of("email", email));

            return e;
        });

        return personMapper.toDto(foundPerson);
    }

    @Override
    @Transactional
    public PersonDto getByPhone(String phone) throws ResourceNotFoundException {
        Person foundPerson = personRepository.findByPhone(phone).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find person with phone: " + phone);

            e.setResourceName("Person");
            e.setFieldsAndValues(Map.of("phone", phone));

            return e;
        });

        return personMapper.toDto(foundPerson);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Can't find person with username (email): " + email));
    }

    private void setRolesIdToPerson(Person person) {
        person.getRoles().forEach(role ->
                roleRepository.findByRole(role.getRole()).ifPresent(val -> role.setId(val.getId()))
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
