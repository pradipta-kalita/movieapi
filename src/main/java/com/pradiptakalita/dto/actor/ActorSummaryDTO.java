package com.pradiptakalita.dto.actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorSummaryDTO {
    private UUID id;
    private String name;
    private String profilePictureUrl;
    private String miniBiography;
    private LocalDate birthDate;
}
