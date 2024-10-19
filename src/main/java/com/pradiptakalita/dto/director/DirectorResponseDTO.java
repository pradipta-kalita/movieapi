package com.pradiptakalita.dto.director;

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
    private LocalDate birthDate;
    private String profilePictureUrl;
    private Set<UUID> movies = new HashSet<>();
}
