package br.com.sobreiraromulo.integrationtestes.dto.wrapper.json;

import br.com.sobreiraromulo.integrationtestes.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookEmbeddedDTO {
    private static final long serialVersionUID = 1L;

    @JsonProperty("books")
    private List<BookDTO> books;

    public BookEmbeddedDTO() {}

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
