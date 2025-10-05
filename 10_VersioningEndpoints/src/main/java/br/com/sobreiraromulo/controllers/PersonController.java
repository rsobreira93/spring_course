package br.com.sobreiraromulo.controllers;

import br.com.sobreiraromulo.data.dto.v1.PersonDTO;
import br.com.sobreiraromulo.data.dto.v2.PersonDTOV2;
import br.com.sobreiraromulo.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable("id") Long id) {
        return personServices.findById(id);
    }

    @GetMapping
    public List<PersonDTO> findAll() {
        return personServices.findAll();
    }


    @PostMapping
    public PersonDTO create(@RequestBody PersonDTO person) {
        return personServices.create(person);
    }

    @PostMapping("v2")
    public PersonDTOV2 create(@RequestBody PersonDTOV2 person) {
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
