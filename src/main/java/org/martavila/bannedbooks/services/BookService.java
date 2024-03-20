package org.martavila.bannedbooks.services;

import org.martavila.bannedbooks.controllers.dto.BookCreateDTO;
import org.martavila.bannedbooks.controllers.dto.BookReadDTO;
import org.martavila.bannedbooks.models.Book;

import java.util.List;

public interface BookService {
    void saveBook (BookCreateDTO bookDTO, String[] genreIds);
    Book findBookByTitle(String title);
    List<BookReadDTO> findAllBooks();
    void deleteBook(String title);
}
