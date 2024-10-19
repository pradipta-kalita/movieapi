package com.pradiptakalita.dto.actor;


import com.pradiptakalita.entity.Movie;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ActorResponseDTO {
    private UUID id;
    private String name;
    private String miniBiography;
    private LocalDate birthDate;
    private String profilePictureUrl;
    private Set<Movie> movies= new HashSet<>();
}
