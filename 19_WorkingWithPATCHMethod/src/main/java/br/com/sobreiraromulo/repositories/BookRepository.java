package br.com.sobreiraromulo.repositories;

import br.com.sobreiraromulo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}