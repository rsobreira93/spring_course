package br.com.sobreiraromulo.services;

import br.com.sobreiraromulo.controllers.PersonController;
import br.com.sobreiraromulo.data.dto.PersonDTO;
import br.com.sobreiraromulo.exceptions.RequiredObjectIsNullException;
import br.com.sobreiraromulo.exceptions.ResourceNotFoudException;
import br.com.sobreiraromulo.model.Person;
import br.com.sobreiraromulo.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.sobreiraromulo.mapper.ObjectMapper.parseListObjects;
import static br.com.sobreiraromulo.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    public PersonDTO create(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Create one person");

        Person entity = parseObject(person, Person.class);

        PersonDTO personDTO = parseObject(personRepository.save(entity), PersonDTO.class);

        addHateoasLinks(personDTO);

        return personDTO;
    }

    public PersonDTO update(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("update one person");

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        PersonDTO personDTO = parseObject(personRepository.save(entity), PersonDTO.class);

        addHateoasLinks(personDTO);

        return personDTO;
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {
        logger.info("disabling one person");

        personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));

        personRepository.disablePerson(id);

        Person entity = personRepository.findById(id).get();

        PersonDTO personDTO = parseObject(entity, PersonDTO.class);

        addHateoasLinks(personDTO);

        return  personDTO;
    }

    public void delete(Long id) {
        logger.info("delete one person");

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));

        personRepository.delete(person);
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all people");

        var persons = parseListObjects(personRepository.findAll(), PersonDTO.class);

        persons.forEach(this::addHateoasLinks);

        return persons;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person");

        Person entity =  personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));

        PersonDTO personDTO = parseObject(entity, PersonDTO.class);

        addHateoasLinks(personDTO);

        return  personDTO;
    }

    private void addHateoasLinks(PersonDTO personDTO) {
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getId()))
                .withSelfRel().withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).findAll())
                .withRel("findAll").withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).create(personDTO))
                .withRel("create").withType("POST"));
        personDTO.add(linkTo(methodOn(PersonController.class).update(personDTO))
                .withRel("update").withType("PUT"));
        personDTO.add(linkTo(methodOn(PersonController.class).disablePerson(personDTO.getId()))
                .withRel("disable").withType("PATCH"));
        personDTO.add(linkTo(methodOn(PersonController.class).delete(personDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }
}
