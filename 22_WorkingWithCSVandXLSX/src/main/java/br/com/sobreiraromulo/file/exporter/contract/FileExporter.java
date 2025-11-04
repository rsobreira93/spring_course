package br.com.sobreiraromulo.file.exporter.contract;

import br.com.sobreiraromulo.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

public interface FileExporter {

    Resource exportFile(List<PersonDTO> persons) throws Exception;
}
