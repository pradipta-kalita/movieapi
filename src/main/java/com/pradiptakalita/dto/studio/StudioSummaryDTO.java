package com.pradiptakalita.dto.studio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioSummaryDTO {
    private UUID id;
    private String name;
    private String description;
    private String studioProfileUrl;
}
