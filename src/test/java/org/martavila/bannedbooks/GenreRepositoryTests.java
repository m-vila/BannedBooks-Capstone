package org.martavila.bannedbooks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martavila.bannedbooks.models.Genre;
import org.martavila.bannedbooks.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GenreRepositoryTests {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void testFindByName() {
        // Arrange
        String genreName = "science fiction";

        // Act
        Genre foundGenre = genreRepository.findByName(genreName);

        // Assert
        Assertions.assertNotNull(foundGenre);
        Assertions.assertEquals(genreName, foundGenre.getName());
    }

    @Test
    public void testFindGenreById() {
        // Arrange
        Long genreId = 3L; //Id 3 = non-fiction

        // Act
        Genre foundGenre = genreRepository.findGenreById(genreId);

        // Assert
        Assertions.assertNotNull(foundGenre);
        Assertions.assertEquals(genreId, foundGenre.getId());
    }
}
