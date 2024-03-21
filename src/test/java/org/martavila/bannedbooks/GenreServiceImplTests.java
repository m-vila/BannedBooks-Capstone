package org.martavila.bannedbooks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martavila.bannedbooks.controllers.dto.GenreDTO;
import org.martavila.bannedbooks.models.Genre;
import org.martavila.bannedbooks.repositories.GenreRepository;
import org.martavila.bannedbooks.services.impl.GenreServiceImpl;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GenreServiceImplTests {
    @Mock
    private GenreRepository genreRepository;

    @Autowired
    private GenreServiceImpl genreService;

    @Test
    public void testFindAllGenres() {
        // Arrange
        Genre genre1 = new Genre();
        genre1.setId(2L);
        genre1.setName("autobiography"); //autobiography has id 2

        Genre genre2 = new Genre();
        genre2.setId(6L);
        genre2.setName("domestic fiction"); //domestic fiction has id 6

        when(genreRepository.findAll()).thenReturn(Arrays.asList(genre1, genre2));

        // Act
        List<GenreDTO> genreDTOs = genreService.findAllGenres();

        // Assert
        Assertions.assertEquals(8, genreDTOs.size()); //In the data initialization part 8 genres were injected
        Assertions.assertEquals(2L, genreDTOs.get(0).getId());
        Assertions.assertEquals("autobiography", genreDTOs.get(0).getName());
        Assertions.assertEquals(6L, genreDTOs.get(1).getId());
        Assertions.assertEquals("domestic fiction", genreDTOs.get(1).getName());
    }
}
