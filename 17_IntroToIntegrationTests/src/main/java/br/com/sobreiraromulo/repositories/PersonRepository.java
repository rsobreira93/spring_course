package br.com.sobreiraromulo.repositories;

import br.com.sobreiraromulo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
