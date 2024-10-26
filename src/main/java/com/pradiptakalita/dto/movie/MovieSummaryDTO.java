package com.pradiptakalita.dto.movie;

import lombok.Data;


import java.util.UUID;

@Data
public class MovieSummaryDTO {
    private UUID id;
    private String title;
    private Integer releaseYear;
    private String posterUrl;
}
