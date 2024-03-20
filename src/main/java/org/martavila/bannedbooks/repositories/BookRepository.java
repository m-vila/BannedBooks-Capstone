package org.martavila.bannedbooks.repositories;

import org.martavila.bannedbooks.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.title = :title")
    Book findByTitle(@Param("title") String title);
    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
    Book findByIsbn(@Param("isbn") String isbn);
}
