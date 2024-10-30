package com.pradiptakalita.dto.movie;

import lombok.Data;


import java.io.Serializable;
import java.util.UUID;

@Data
public class MovieSummaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String title;
    private Integer releaseYear;
    private String posterUrl;
}
