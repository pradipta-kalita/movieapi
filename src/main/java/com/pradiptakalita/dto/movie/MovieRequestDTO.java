package com.pradiptakalita.dto.movie;

import com.pradiptakalita.validation.ValidReleaseYear;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDTO {

    private UUID id;

    @NotBlank(message = "Please enter a movie title.")
    @Size(min = 1,max = 255, message = "Title must be between 1 and 255 characters.")
    private String title;

    @NotNull(message = "Please enter the release year of the movie.")
    @ValidReleaseYear
    private Integer releaseYear;

    @NotBlank(message = "Movie poster is required.")
    @Size(min = 1, max = 2000, message = "The movie poster url must be between 1 and 2000 characters.")
    private String poster;

    @NotNull(message = "Please provide a studio ID.")
    private UUID studioId;

    private Set<UUID> directorIds;

    private Set<UUID> actorIds;
}
