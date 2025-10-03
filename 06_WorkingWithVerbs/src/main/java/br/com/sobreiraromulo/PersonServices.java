package br.com.sobreiraromulo;

import br.com.sobreiraromulo.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person create(Person person) {
        logger.info("Create one person");

        return person;
    }

    public Person update(Person person) {
        logger.info("update one person");

        return person;
    }

    public void delete(String id) {
        logger.info("dele one person");
    }



    public List<Person> findAll() {
        logger.info("Finding all people");

        List<Person> persons = new ArrayList<Person>();

        for(int i = 0; i < 8; i++) {
            Person person = mockPerson(i);

            persons.add(person);
        }


        return  persons;
    }

    public Person findById(String id) {
        logger.info("Finding one person");


        Person person = new Person();

        person.setId(counter.incrementAndGet());
        person.setFirstName("Romulo");
        person.setLastName("Sobreira");
        person.setAddress("Pau dos ferros - RN");
        person.setGender("MALE");

        return person;
    }

    private Person mockPerson(int i) {
        Person person = new Person();

        person.setId(counter.incrementAndGet());
        person.setFirstName("FirstName " + i);
        person.setLastName("LastName " + i);
        person.setAddress("Some address in Brasil");
        person.setGender("MALE");

        return  person;

    }

}
