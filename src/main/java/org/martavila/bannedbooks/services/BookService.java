package org.martavila.bannedbooks.services;

import org.martavila.bannedbooks.controllers.dto.BookDTO;
import org.martavila.bannedbooks.models.Book;

import java.util.List;

public interface BookService {
    void saveBook (BookDTO bookDTO);
    Book findBookByTitle(String title);
    Book findBookByIsbn(String isbn);
    List<BookDTO> findAllBooks();
    void updateBook(String title, BookDTO bookDTO);
    void deleteBook(String title);
}
