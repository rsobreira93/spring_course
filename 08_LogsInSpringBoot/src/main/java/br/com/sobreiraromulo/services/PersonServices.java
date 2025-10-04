package br.com.sobreiraromulo.services;

import br.com.sobreiraromulo.controllers.TestLogController;
import br.com.sobreiraromulo.exceptions.ResourceNotFoudException;
import br.com.sobreiraromulo.model.Person;
import br.com.sobreiraromulo.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    public Person create(Person person) {
        logger.info("Create one person");

        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("update one person");

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(Long id) {
        logger.info("dele one person");

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));

        personRepository.delete(person);
    }

    public List<Person> findAll() {
        logger.info("Finding all people");


        return personRepository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one person");

        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));
    }
}
