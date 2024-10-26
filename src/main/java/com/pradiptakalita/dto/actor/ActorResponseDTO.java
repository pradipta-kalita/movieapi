package com.pradiptakalita.dto.actor;

import com.pradiptakalita.entity.Movie;
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
    private Set<Movie> movies=new HashSet<>();
}
