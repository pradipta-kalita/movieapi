package com.pradiptakalita.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class MovieResponseDTO {

    private UUID id;
    private String title;
    private Integer releaseYear;
    private String posterUrl;

    // Including detailed information about the studio
    // private StudioDTO studio;

    // Including details about directors and actors
    // private Set<PersonDTO> directors;
    // private Set<PersonDTO> actors;


}