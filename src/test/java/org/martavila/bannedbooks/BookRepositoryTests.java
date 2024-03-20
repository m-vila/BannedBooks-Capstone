package org.martavila.bannedbooks;

import org.junit.jupiter.api.extension.ExtendWith;
import org.martavila.bannedbooks.models.Book;
import org.martavila.bannedbooks.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByTitle() {
        // Arrange
        String title = "The Color Purple";

        // Act
        Book foundBook = bookRepository.findByTitle(title);

        // Assert
        Assertions.assertNotNull(foundBook);
        Assertions.assertEquals(title, foundBook.getTitle());
    }

    @Test
    public void testFindByIsbn() {
        // Arrange
        String isbn = "978-0062696120";

        // Act
        Book foundBook = bookRepository.findByIsbn(isbn);

        // Assert
        Assertions.assertNotNull(foundBook);
        Assertions.assertEquals(isbn, foundBook.getIsbn());
    }
}
