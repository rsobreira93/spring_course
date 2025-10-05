package br.com.sobreiraromulo.controllers;

import br.com.sobreiraromulo.data.dto.PersonDTO;
import br.com.sobreiraromulo.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/person/v1")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable("id") Long id) {
        PersonDTO person =  personServices.findById(id);

        person.setBirthDay(new Date());
        /*person.setPhoneNumber("+55 (84) 92144-1987");*/
        person.setPhoneNumber("");
        person.setLastName(null);
        person.setSensitiveData("foo ");

        return  person;
    }

    @GetMapping
    public List<PersonDTO> findAll() {
        return personServices.findAll();
    }


    @PostMapping
    public PersonDTO create(@RequestBody PersonDTO person) {
        return personServices.create(person);
    }

    @PutMapping
    public PersonDTO update(@RequestBody PersonDTO person) {
        return personServices.update(person);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        personServices.delete(id);

        return  ResponseEntity.noContent().build();
    }
}
