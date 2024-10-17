package com.pradiptakalita.dto.director;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DirectorSummaryDTO {
    private UUID id;
    private String name;
    private String profilePictureUrl;
}
