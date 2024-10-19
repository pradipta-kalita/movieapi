package com.pradiptakalita.dto.studio;

import com.pradiptakalita.entity.Movie;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class StudioResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private String studioProfileUrl;
    private Set<Movie> movies = new HashSet<>();
}
