package org.martavila.bannedbooks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.martavila.bannedbooks.controllers.dto.BookReadDTO;
import org.martavila.bannedbooks.exceptions.BookNotFoundException;
import org.martavila.bannedbooks.models.Book;
import org.martavila.bannedbooks.repositories.BookRepository;
import org.martavila.bannedbooks.services.impl.BookServiceImpl;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookServiceImplTests {
    @Mock
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImpl bookService;

    @ParameterizedTest
    @ValueSource(strings = {"Gender Queer: A Memoir", "The Color Purple", "To Kill a Mockingbird", "Brave New World"})
    public void testFindAllBooks(String title) {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Gender Queer: A Memoir");
        Book book2 = new Book();
        book2.setTitle("The Color Purple");
        Book book3 = new Book();
        book3.setTitle("To Kill a Mockingbird");
        Book book4 = new Book();
        book4.setTitle("Brave New World");

        // Act
        List<BookReadDTO> books = bookService.findAllBooks();

        // Assert
        Assertions.assertTrue(books.stream().anyMatch(dto -> dto.getTitle().equals(title)));
    }

    //Negative test - BookNotFoundException will be thrown
    @Test
    public void testDeleteBook_NotFound() {
        // Arrange
        String isbn = "1234567890";
        when(bookRepository.findByIsbn(isbn)).thenReturn(null);

        // Act
        Assertions.assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook(isbn);
        });

        // Assert
        verify(bookRepository, never()).delete(any(Book.class));
    }
}
