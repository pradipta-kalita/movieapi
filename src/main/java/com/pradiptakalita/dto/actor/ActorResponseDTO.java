package com.pradiptakalita.dto.actor;

import com.pradiptakalita.dto.movie.MovieSummaryDTO;
import com.pradiptakalita.entity.Movie;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ActorResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String name;
    private String miniBiography;
    private String birthDate;
    private String profilePictureUrl;
    private Set<MovieSummaryDTO> movies=new HashSet<>();
}
