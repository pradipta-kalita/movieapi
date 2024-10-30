package com.pradiptakalita.dto.director;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorSummaryDTO {
    private UUID id;
    private String name;
    private String profilePictureUrl;
    private String miniBiography;
    private String birthDate;
}
