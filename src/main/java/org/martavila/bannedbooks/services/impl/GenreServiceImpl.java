package org.martavila.bannedbooks.services.impl;

import org.martavila.bannedbooks.controllers.dto.GenreDTO;
import org.martavila.bannedbooks.models.Genre;
import org.martavila.bannedbooks.repositories.GenreRepository;
import org.martavila.bannedbooks.services.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        super();
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDTO> findAllGenres() {
        List<Genre> genres = genreRepository.findAll();

        return genres.stream()
                .map((genre) -> mapToGenreDTO(genre))
                .collect(Collectors.toList());
    }

    private GenreDTO mapToGenreDTO(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());

        return genreDTO;
    }

    @Override
    public Genre findGenreById(Long id) {
        return genreRepository.findGenreById(id);
    }
}
