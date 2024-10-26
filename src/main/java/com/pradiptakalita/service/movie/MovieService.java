package com.pradiptakalita.service.movie;

import com.pradiptakalita.dto.movie.MoviePageResponseDTO;
import com.pradiptakalita.dto.movie.MovieRequestDTO;
import com.pradiptakalita.dto.movie.MovieResponseDTO;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    MovieResponseDTO getMovieById(UUID id);
    MoviePageResponseDTO getAllMovies(int page, int size, String sortBy,String order);
    MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO);
    MovieResponseDTO updateMovieById(MovieRequestDTO movieRequestDTO, UUID id);
    String deleteMovieById(UUID id);
}
