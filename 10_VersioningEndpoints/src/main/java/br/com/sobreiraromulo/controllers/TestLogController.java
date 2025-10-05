package br.com.sobreiraromulo.controllers;

import br.com.sobreiraromulo.services.PersonServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestLogController {

    private final Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());


    @GetMapping("test")
    public String testLog() {
        logger.debug("this is a DEBUG log");
        logger.info("this is an INFO log");
        logger.warn("this is a WARN log");
        logger.error("this is an ERROR log");
        return "Logs generated successfully";
    }
}
