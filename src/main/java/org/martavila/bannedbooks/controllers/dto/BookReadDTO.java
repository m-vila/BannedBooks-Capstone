package org.martavila.bannedbooks.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
 This DTO class represents a read-only view of a book,
 used for displaying book information.
 This is why we needed to include the list of genres associated with the book.
 */

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
