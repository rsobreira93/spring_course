package br.com.sobreiraromulo.file.importer.factory;

import br.com.sobreiraromulo.exceptions.BadRequestException;
import br.com.sobreiraromulo.file.importer.contract.FileImporter;
import br.com.sobreiraromulo.file.importer.implemetation.CsvImporter;
import br.com.sobreiraromulo.file.importer.implemetation.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileImporterFactory {

    private Logger LOG = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext applicationContext;

    public FileImporter getImporter(String fileName) throws  Exception {
        if (fileName.endsWith(".xlsx")) {
//            return new XlsxImporter();
            return applicationContext.getBean(XlsxImporter.class);
        } else if (fileName.endsWith(".csv")) {
//            return new CsvImporter();
            return applicationContext.getBean(CsvImporter.class);
        } else {
            throw new BadRequestException("Invalid file format.");
        }
    }
}
