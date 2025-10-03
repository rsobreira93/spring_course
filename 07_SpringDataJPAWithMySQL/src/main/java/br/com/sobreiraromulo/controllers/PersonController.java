package br.com.sobreiraromulo.controllers;

import br.com.sobreiraromulo.services.PersonServices;
import br.com.sobreiraromulo.model.Person;
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
    public Person findById(@PathVariable("id") Long id) {
        return personServices.findById(id);
    }

    @GetMapping
    public List<Person> findAll() {
        return personServices.findAll();
    }


    @PostMapping
    public Person create(@RequestBody Person person) {
        return personServices.create(person);
    }

    @PutMapping
    public Person update(@RequestBody Person person) {
        return personServices.update(person);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        personServices.delete(id);

        return  ResponseEntity.noContent().build();
    }
}
