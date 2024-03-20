package org.martavila.bannedbooks.services;

import org.martavila.bannedbooks.controllers.dto.BookDTO;
import org.martavila.bannedbooks.controllers.dto.GenreDTO;
import org.martavila.bannedbooks.models.Book;

import java.util.List;

public interface BookService {
    void saveBook (BookDTO bookDTO, String[] genreIds);
    Book findBookByTitle(String title);
    List<BookDTO> findAllBooks();
    void deleteBook(String title);
}
