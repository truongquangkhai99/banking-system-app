package com.unvise.bankingsystemapp.domain.person.person;

import com.unvise.bankingsystemapp.domain.common.BaseRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {

    @EntityGraph(value = "person-account-roles-graph")
    @Override
    List<Person> findAll();

    @EntityGraph(value = "person-account-roles-graph")
    @Override
    Optional<Person> findById(Long aLong);

    @EntityGraph(value = "person-account-roles-graph")
    List<Person> findByFirstname(String firstname);

    @EntityGraph(value = "person-account-roles-graph")
    List<Person> findByLastname(String lastname);

    @EntityGraph(value = "person-account-roles-graph")
    List<Person> findByFirstnameAndLastname(String firstname, String lastname);

    @EntityGraph(value = "person-account-roles-graph")
    Optional<Person> findByEmail(String email);

    @EntityGraph(value = "person-account-roles-graph")
    Optional<Person> findByPhone(String phone);

}
