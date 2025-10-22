package br.com.sobreiraromulo.integrationtestes.dto.wrapper.json;

import br.com.sobreiraromulo.integrationtestes.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PersonEmbeddedDTO {
    private static final long serialVersionUID = 1L;

    @JsonProperty("people")
    private List<PersonDTO> people;

    public PersonEmbeddedDTO() {}

    public List<PersonDTO> getPeople() {
        return people;
    }

    public void setPeople(List<PersonDTO> people) {
        this.people = people;
    }
}
