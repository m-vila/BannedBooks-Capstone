package org.martavila.bannedbooks.repositories;

import org.martavila.bannedbooks.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
    Genre findGenreById(Long id);
}
