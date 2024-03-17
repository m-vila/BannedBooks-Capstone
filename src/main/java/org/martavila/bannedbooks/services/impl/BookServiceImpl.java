package org.martavila.bannedbooks.services.impl;

import org.martavila.bannedbooks.controllers.dto.BookDTO;
import org.martavila.bannedbooks.models.Book;
import org.martavila.bannedbooks.repositories.BookRepository;
import org.martavila.bannedbooks.repositories.GenreRepository;
import org.martavila.bannedbooks.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, GenreRepository genreRepository) {
        super();
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public void saveBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setYear(bookDTO.getYear());

        bookRepository.save(book);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<BookDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map((book) -> mapToBookDTO(book))
                .collect(Collectors.toList());
    }

    private BookDTO mapToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setYear(book.getYear());

        return bookDTO;
    }
}
