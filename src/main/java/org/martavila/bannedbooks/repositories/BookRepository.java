package org.martavila.bannedbooks.repositories;

import org.martavila.bannedbooks.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
    Book findByIsbn(String isbn);
}
