package br.com.sobreiraromulo.services;

import br.com.sobreiraromulo.controllers.PersonController;
import br.com.sobreiraromulo.data.dto.PersonDTO;
import br.com.sobreiraromulo.exceptions.BadRequestException;
import br.com.sobreiraromulo.exceptions.FileStorageException;
import br.com.sobreiraromulo.exceptions.RequiredObjectIsNullException;
import br.com.sobreiraromulo.exceptions.ResourceNotFoundException;
import br.com.sobreiraromulo.file.exporter.contract.FileExporter;
import br.com.sobreiraromulo.file.exporter.factory.FileExporterFactory;
import br.com.sobreiraromulo.file.importer.contract.FileImporter;
import br.com.sobreiraromulo.file.importer.factory.FileImporterFactory;
import br.com.sobreiraromulo.model.Person;
import br.com.sobreiraromulo.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static br.com.sobreiraromulo.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PagedResourcesAssembler<PersonDTO> assembler;

    @Autowired
    FileImporterFactory fileImporterFactory;

    @Autowired
    FileExporterFactory fileExporterFactory;

    public PersonDTO create(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Create one person");

        Person entity = parseObject(person, Person.class);

        PersonDTO personDTO = parseObject(personRepository.save(entity), PersonDTO.class);

        addHateoasLinks(personDTO);

        return personDTO;
    }

    public List<PersonDTO> massCreation(MultipartFile file) {
        logger.info("Importing people from file");

        if(file.isEmpty()){
            throw new BadRequestException("Please set a valid file");
        }

        try(InputStream inputStream = file.getInputStream()){
            String fileName = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(()-> new BadRequestException("File name cannot be null"));

            FileImporter fileImporter = this.fileImporterFactory.getImporter(fileName);

            List<Person> entities = fileImporter.importFile(inputStream)
                    .stream()
                    .map(dto ->
                        personRepository.save(parseObject(dto, Person.class)))
                    .toList();

            return entities.stream()
                    .map(entity -> {
                        var dto = parseObject(entity, PersonDTO.class);
                        addHateoasLinks(dto);
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new FileStorageException("Error processing the file.");
        }
    }

    public PersonDTO update(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("update one person");

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        personRepository.disablePerson(id);

        Person entity = personRepository.findById(id).get();

        PersonDTO personDTO = parseObject(entity, PersonDTO.class);

        addHateoasLinks(personDTO);

        return  personDTO;
    }

    public void delete(Long id) {
        logger.info("delete one person");

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        personRepository.delete(person);
    }

    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {
        logger.info("Finding all people");

        Page<Person> people = personRepository.findAll(pageable);

        return buildPagedModel(pageable, people);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person");

        Person entity =  personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        PersonDTO personDTO = parseObject(entity, PersonDTO.class);

        addHateoasLinks(personDTO);

        return  personDTO;
    }

    public Resource exportPage(Pageable pageable, String acceptHeader) {
        logger.info("Exporting a people page");

        List<PersonDTO> people = personRepository.findAll(pageable)
                .map(person -> parseObject(person, PersonDTO.class))
                .getContent();

        try {
            FileExporter exporter = this.fileExporterFactory.getExporter(acceptHeader);
            return exporter.exportFile(people);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export", e);
        }
    }

    public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable) {

        logger.info("Finding People by name!");

        var people = personRepository.findPeopleByName(firstName, pageable);

        return buildPagedModel(pageable, people);
    }

    private PagedModel<EntityModel<PersonDTO>> buildPagedModel(Pageable pageable, Page<Person> people) {
        var peopleWithLinks = people.map(person -> {
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(PersonController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(peopleWithLinks, findAllLink);
    }

    private void addHateoasLinks(PersonDTO personDTO) {
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getId()))
                .withSelfRel().withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).findAll(1, 10, "asc"))
                .withRel("findAll").withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).findByName("",1, 10, "asc"))
                .withRel("findByName").withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).create(personDTO))
                .withRel("create").withType("POST"));
        personDTO.add(linkTo(methodOn(PersonController.class)).slash("massCreation")
                .withRel("massCreation").withType("POST"));
        personDTO.add(linkTo(methodOn(PersonController.class).update(personDTO))
                .withRel("update").withType("PUT"));
        personDTO.add(linkTo(methodOn(PersonController.class).disablePerson(personDTO.getId()))
                .withRel("disable").withType("PATCH"));
        personDTO.add(linkTo(methodOn(PersonController.class).delete(personDTO.getId()))
                .withRel("delete").withType("DELETE"));

        personDTO.add(linkTo(methodOn(PersonController.class)
                .exportPage(
                        1, 12, "asc", null))
                .withRel("exportPage")
                .withType("GET")
                .withTitle("Export People")
        );
    }
}
