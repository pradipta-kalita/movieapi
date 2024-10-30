package com.pradiptakalita.dto.director;

import com.pradiptakalita.dto.movie.MovieSummaryDTO;
import com.pradiptakalita.entity.Movie;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class DirectorResponseDTO {
    private UUID id;
    private String name;
    private String miniBiography;
    private String birthDate;
    private String profilePictureUrl;
    private Set<MovieSummaryDTO> movies = new HashSet<>();
}
