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
        // Create a new Book object based on the BookCreateDTO
        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setYear(bookDTO.getYear());

        // Map genreIds to Genre objects and set them to the book's genres
        List<Genre> genres = Arrays.stream(genreIds)
                .map(Long::parseLong)
                .map(genreRepository::findGenreById)
                .collect(Collectors.toList());
        book.setGenres(genres);

        // Persist the book to the database
        bookRepository.save(book);
    }

    @Override
    public List<BookReadDTO> findAllBooks() {

        // Retrieve all books from the database and map them to BookReadDTOs
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map((book) -> mapToBookDTO(book))
                .collect(Collectors.toList());
    }

    private BookReadDTO mapToBookDTO(Book book) {

        // Map a Book object to a BookReadDTO
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

        // Map a Genre object to a GenreDTO
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());

        return genreDTO;
    }


    @Override
    public void deleteBook(String isbn) {

        // Find the book by its ISBN
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
            // Throw a custom exception if the book is not found
            throw new BookNotFoundException("The book you are trying to delete with ISBN " + isbn + " was not found");
        }
    }

}