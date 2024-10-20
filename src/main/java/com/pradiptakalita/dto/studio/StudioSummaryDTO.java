package com.pradiptakalita.dto.studio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StudioSummaryDTO {
    private UUID id;
    private String name;
    private String studioProfileUrl;
}
