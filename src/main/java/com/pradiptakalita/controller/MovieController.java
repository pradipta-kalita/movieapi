package com.pradiptakalita.controller;

import com.pradiptakalita.dto.movie.MoviePageResponseDTO;
import com.pradiptakalita.dto.movie.MovieRequestDTO;
import com.pradiptakalita.dto.movie.MovieResponseDTO;
import com.pradiptakalita.entity.Movie;
import com.pradiptakalita.service.movie.MovieService;
import com.pradiptakalita.utils.AppConstants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<MoviePageResponseDTO> getAllMovies(@RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
                                                             @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
                                                             @RequestParam(defaultValue = AppConstants.MOVIE_SORT_BY,required = false) String sortBy,
                                                             @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order){
        return ResponseEntity.ok().body(movieService.getAllMovies(page,size,sortBy,order));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieResponseDTO> createMovie(@ModelAttribute MovieRequestDTO movieRequestDTO){
        return ResponseEntity.ok().body(movieService.createMovie(movieRequestDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable UUID id){
        return ResponseEntity.ok().body( movieService.deleteMovieById(id));
    }
}
