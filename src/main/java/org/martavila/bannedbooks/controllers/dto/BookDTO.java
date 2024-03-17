package org.martavila.bannedbooks.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private String isbn;
    private String title;
    private String author;
    private Integer year;

}
