package org.martavila.bannedbooks.services;

import org.martavila.bannedbooks.controllers.dto.GenreDTO;
import org.martavila.bannedbooks.models.Genre;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAllGenres();
    Genre findGenreById (Long id);
}
