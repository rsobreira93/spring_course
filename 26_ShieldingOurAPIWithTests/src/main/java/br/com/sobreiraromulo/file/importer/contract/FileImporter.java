package br.com.sobreiraromulo.file.importer.contract;

import br.com.sobreiraromulo.data.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
