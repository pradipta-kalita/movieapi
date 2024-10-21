package com.pradiptakalita.service.movie;

import com.pradiptakalita.dto.movie.MovieRequestDTO;
import com.pradiptakalita.dto.movie.MovieResponseDTO;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    MovieResponseDTO getMovieById(UUID id);
    List<MovieResponseDTO> getAllMovies();
    MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO);
    MovieResponseDTO updateMovieById(MovieRequestDTO movieRequestDTO, UUID id);
    String deleteMovieById(UUID id);
}
