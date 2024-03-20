package org.martavila.bannedbooks.services.impl;

import org.martavila.bannedbooks.controllers.dto.BookCreateDTO;
import org.martavila.bannedbooks.controllers.dto.BookReadDTO;
import org.martavila.bannedbooks.controllers.dto.GenreDTO;
import org.martavila.bannedbooks.exceptions.BookNotFoundException;
import org.martavila.bannedbooks.models.Book;
import org.martavila.bannedbooks.models.Genre;
import org.martavila.bannedbooks.repositories.BookRepository;
import org.martavila.bannedbooks.repositories.GenreRepository;
import org.martavila.bannedbooks.services.BookService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, GenreRepository genreRepository) {
        super();
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public void saveBook(BookCreateDTO bookDTO, String[] genreIds) {
        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setYear(bookDTO.getYear());

        List<Genre> genres = Arrays.stream(genreIds)
                .map(Long::parseLong)
                .map(genreRepository::findGenreById)
                .collect(Collectors.toList());

        book.setGenres(genres);

        bookRepository.save(book);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<BookReadDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map((book) -> mapToBookDTO(book))
                .collect(Collectors.toList());
    }

    private BookReadDTO mapToBookDTO(Book book) {
        BookReadDTO bookReadDTO = new BookReadDTO();

        bookReadDTO.setIsbn(book.getIsbn());
        bookReadDTO.setTitle(book.getTitle());
        bookReadDTO.setAuthor(book.getAuthor());
        bookReadDTO.setYear(book.getYear());
        bookReadDTO.setGenres(book.getGenres().stream()
                .map(genre -> mapToGenreDTO(genre))
                .collect(Collectors.toList()));

        return bookReadDTO;
    }

    private GenreDTO mapToGenreDTO(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());

        return genreDTO;
    }


    @Override
    public void deleteBook(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book != null) {
            //Remove the book from all genres it's associated with
            for (Genre genre : book.getGenres()) {
                genre.getBooks().remove(book);
            }

            //Save the changes to the genres
            genreRepository.saveAll(book.getGenres());

            //Now delete the book
            bookRepository.delete(book);
        } else {
            throw new BookNotFoundException("The book you are trying to delete with ISBN " + isbn + " was not found");
        }
    }

}