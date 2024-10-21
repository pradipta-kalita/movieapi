package com.pradiptakalita.controller;

import com.pradiptakalita.dto.movie.MovieRequestDTO;
import com.pradiptakalita.dto.movie.MovieResponseDTO;
import com.pradiptakalita.entity.Movie;
import com.pradiptakalita.service.movie.MovieService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable UUID id){
        return ResponseEntity.ok().body(movieService.getMovieById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies(){
        return ResponseEntity.ok().body(movieService.getAllMovies());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieResponseDTO> createMovie(@ModelAttribute MovieRequestDTO movieRequestDTO){
        return ResponseEntity.ok().body(movieService.createMovie(movieRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable UUID id){
        return ResponseEntity.ok().body( movieService.deleteMovieById(id));
    }
}
