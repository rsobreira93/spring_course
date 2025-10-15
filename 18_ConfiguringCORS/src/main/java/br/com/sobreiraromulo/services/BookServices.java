package br.com.sobreiraromulo.services;

import br.com.sobreiraromulo.controllers.BookController;
import br.com.sobreiraromulo.data.dto.BookDTO;
import br.com.sobreiraromulo.exceptions.RequiredObjectIsNullException;
import br.com.sobreiraromulo.exceptions.ResourceNotFoudException;
import br.com.sobreiraromulo.model.Book;
import br.com.sobreiraromulo.repositories.BookRepository;
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
public class BookServices {

    private final Logger logger = LoggerFactory.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository bookRepository;


    public List<BookDTO> findAll() {

        logger.info("Finding all Book!");

        var books = parseListObjects(bookRepository.findAll(), BookDTO.class);
        books.forEach(this::addHateoasLinks);

        return books;
    }

    public BookDTO findById(Long id) {
        logger.info("Finding one Book!");

        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoudException("No records found for this ID!"));

        BookDTO dto =  parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public BookDTO create(BookDTO book) {

        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Book!");
        Book entity = parseObject(book, Book.class);

        BookDTO dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public BookDTO update(BookDTO book) {

        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Book!");
        Book entity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoudException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        BookDTO dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one Book!");

        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoudException("No records found for this ID!"));

        bookRepository.delete(entity);
    }

    private void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}