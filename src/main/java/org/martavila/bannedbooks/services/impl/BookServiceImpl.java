package org.martavila.bannedbooks.services.impl;

import org.martavila.bannedbooks.controllers.dto.BookDTO;
import org.martavila.bannedbooks.exceptions.BookNotFoundException;
import org.martavila.bannedbooks.models.Book;
import org.martavila.bannedbooks.models.Genre;
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
    public Book findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
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

    @Override
    public void updateBook(String isbn, BookDTO bookDTO) {
        Book book = bookRepository.findByIsbn(isbn);

        if (book == null) {
            throw new BookNotFoundException("Book not found with ISBN: " + isbn);
        }

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setYear(bookDTO.getYear());
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(String title) {
        Book book = bookRepository.findByTitle(title);
        if (book != null) {
            //Remove the book from all genres it's associated with
            for (Genre genre : book.getGenres()) {
                genre.getBooks().remove(book);
            }
            //Clear the list of genres associated with the book
            book.getGenres().clear();
            //Save the changes to the genres
            genreRepository.saveAll(book.getGenres());

            //Now delete the book
            bookRepository.delete(book);
        } else {
            throw new BookNotFoundException("The book you are trying to delete with title " + title + " was not found");
        }
    }

}