package com.unvise.bankingsystemapp.domain.person.person;

import com.unvise.bankingsystemapp.common.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {

    List<Person> findByFirstname(String firstname);

    List<Person> findByLastname(String lastname);

    List<Person> findByFirstnameAndLastname(String firstname, String lastname);

    Optional<Person> findByEmail(String email);

    Optional<Person> findByPhone(String phone);

}
