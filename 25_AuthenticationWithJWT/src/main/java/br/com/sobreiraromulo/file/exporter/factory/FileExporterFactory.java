package br.com.sobreiraromulo.file.exporter.factory;

import br.com.sobreiraromulo.exceptions.BadRequestException;
import br.com.sobreiraromulo.file.exporter.MediaTypes;
import br.com.sobreiraromulo.file.exporter.contract.PersonExporter;
import br.com.sobreiraromulo.file.exporter.implementation.CsvExporter;
import br.com.sobreiraromulo.file.exporter.implementation.PdfExporter;
import br.com.sobreiraromulo.file.exporter.implementation.XlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    private Logger LOG = LoggerFactory.getLogger(FileExporterFactory.class);


    @Autowired
    private ApplicationContext applicationContext;

    public PersonExporter getExporter(String acceptHeader) throws  Exception {
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
//            return new XlsxImporter();
            return applicationContext.getBean(XlsxExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
//            return new CsvImporter();
            return applicationContext.getBean(CsvExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_PDF_VALUE)) {
//            return new CsvImporter();
            return applicationContext.getBean(PdfExporter.class);
        }
        else {
            throw new BadRequestException("Invalid file format.");
        }
    }
}
