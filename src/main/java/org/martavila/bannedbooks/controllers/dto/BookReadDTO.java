package org.martavila.bannedbooks.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookReadDTO {
    @NotEmpty
    private String isbn;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    private Integer year;

    private List<GenreDTO> genres;
}
