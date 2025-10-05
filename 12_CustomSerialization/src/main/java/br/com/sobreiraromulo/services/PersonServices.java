package br.com.sobreiraromulo.services;

import br.com.sobreiraromulo.data.dto.PersonDTO;
import br.com.sobreiraromulo.exceptions.ResourceNotFoudException;
import br.com.sobreiraromulo.model.Person;
import br.com.sobreiraromulo.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.sobreiraromulo.mapper.ObjectMapper.parseListObjects;
import static br.com.sobreiraromulo.mapper.ObjectMapper.parseObject;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    public PersonDTO create(PersonDTO person) {
        logger.info("Create one person");

        Person entity = parseObject(person, Person.class);

        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("update one person");

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("dele one person");

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));

        personRepository.delete(person);
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all people");


        return parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person");

        Person entity =  personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoudException("Person not found"));

        return parseObject(entity, PersonDTO.class);
    }
}
