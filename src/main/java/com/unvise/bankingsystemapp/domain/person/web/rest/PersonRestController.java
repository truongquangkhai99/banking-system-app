package com.unvise.bankingsystemapp.domain.person.web.rest;

import com.unvise.bankingsystemapp.domain.common.View;
import com.unvise.bankingsystemapp.domain.person.person.PersonService;
import com.unvise.bankingsystemapp.domain.person.web.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/person")
@RequiredArgsConstructor
public class PersonRestController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDto>> getPersons() {
        return new ResponseEntity<>(personService.getAll(), HttpStatus.OK);
    }

    @GetMapping(params = "firstname")
    public ResponseEntity<List<PersonDto>> getPersonsByFirstname(@RequestParam String firstname) {
        return new ResponseEntity<>(personService.getByFirstname(firstname), HttpStatus.OK);
    }

    @GetMapping(params = "lastname")
    public ResponseEntity<List<PersonDto>> getPersonsByLastname(@RequestParam String lastname) {
        return new ResponseEntity<>(personService.getByLastname(lastname), HttpStatus.OK);
    }

    @GetMapping(params = {"firstname", "lastname"})
    public ResponseEntity<List<PersonDto>> getPersonsByFirstnameAndLastname(@RequestParam String firstname,
                                                                            @RequestParam String lastname) {
        return new ResponseEntity<>(personService.getByFirstnameAndLastname(firstname, lastname), HttpStatus.OK);
    }

    @GetMapping(params = "email")
    public ResponseEntity<PersonDto> getPersonsByEmail(@RequestParam String email) {
        return new ResponseEntity<>(personService.getByEmail(email), HttpStatus.OK);
    }

    @GetMapping(params = "phone")
    public ResponseEntity<PersonDto> getPersonsByPhone(@RequestParam String phone) {
        return new ResponseEntity<>(personService.getByPhone(phone), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long id) {
        return new ResponseEntity<>(personService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(@Validated({View.New.class})
                                               @RequestBody PersonDto personDto) {
        return new ResponseEntity<>(personService.save(personDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> fullyUpdatePerson(@PathVariable Long id,
                                                       @Validated({View.Update.class})
                                                       @RequestBody PersonDto personDto) {
        return new ResponseEntity<>(personService.updateById(id, personDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonDto> deletePerson(@PathVariable Long id) {
        return new ResponseEntity<>(personService.deleteById(id), HttpStatus.OK);
    }

}
