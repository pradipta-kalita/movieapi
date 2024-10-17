package com.pradiptakalita.mapper;

import com.pradiptakalita.dto.director.DirectorRequestDTO;
import com.pradiptakalita.dto.director.DirectorResponseDTO;
import com.pradiptakalita.entity.Director;
import com.pradiptakalita.entity.Movie;
import lombok.Data;

import java.util.*;


public class DirectorMapper {

    public static Director toEntity(DirectorRequestDTO directorRequestDTO) {
        if (directorRequestDTO == null) {
            return null;
        }
        Director director = new Director();
        director.setId(UUID.randomUUID());
        director.setName(directorRequestDTO.getName());
        director.setMiniBiography(directorRequestDTO.getMiniBiography());
        director.setBirthDate(directorRequestDTO.getBirthDate());

        return director;
    }


    public static DirectorResponseDTO toResponseDTO(Director director) {
        if (director == null) {
            return null;
        }
        DirectorResponseDTO directorResponseDTO = new DirectorResponseDTO();
        directorResponseDTO.setId(director.getId());
        directorResponseDTO.setName(director.getName());
        directorResponseDTO.setMiniBiography(director.getMiniBiography());
        directorResponseDTO.setBirthDate(director.getBirthDate());
        directorResponseDTO.setProfilePictureUrl(director.getProfilePictureUrl());
//        Set<Movie> movies = new HashSet<>(director.getMovies());
        
        return directorResponseDTO;
    }
//    public List<Movie> extractMovieDetails(Director director) {
//        if (director == null || director.getMovies() == null) {
//            return List.of(); // Return an empty list if director or movies are null
//        }
//
//        Set<Movie> movies = director.getMovies();
//
//        // Using stream to map movies to MovieDTO
//        return movies.stream().map(movie -> {
//            Movie movieDTO = new Movie();
//            movieDTO.setMovieId(movie.getId());
//            movieDTO.setName(movie.getName());
//            movieDTO.setPosterUrl(movie.getPosterUrl());
//            return movieDTO;
//        }).collect(Collectors.toList());
//    }
}
