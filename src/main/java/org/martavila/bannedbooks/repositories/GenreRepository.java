package org.martavila.bannedbooks.repositories;

import org.martavila.bannedbooks.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("SELECT g FROM Genre g WHERE g.name = :name")
    Genre findByName(@Param("name")String name);
    @Query("SELECT g FROM Genre g WHERE g.id = :id")
    Genre findGenreById(@Param("id") Long id);
}
