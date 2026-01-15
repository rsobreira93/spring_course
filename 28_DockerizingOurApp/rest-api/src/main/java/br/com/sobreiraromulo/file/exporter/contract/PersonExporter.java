package br.com.sobreiraromulo.file.exporter.contract;

import br.com.sobreiraromulo.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface PersonExporter {

    Resource exportPeople(List<PersonDTO> persons) throws Exception;
    Resource exportPerson(PersonDTO person) throws Exception;
}
