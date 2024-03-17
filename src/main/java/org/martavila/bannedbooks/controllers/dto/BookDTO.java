package org.martavila.bannedbooks.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    @NotEmpty
    private String isbn;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    private Integer year;
}
