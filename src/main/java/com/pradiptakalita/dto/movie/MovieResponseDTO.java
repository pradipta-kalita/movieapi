package com.pradiptakalita.dto.movie;

import com.pradiptakalita.dto.actor.ActorSummaryDTO;
import com.pradiptakalita.dto.director.DirectorSummaryDTO;
import com.pradiptakalita.entity.Studio;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class MovieResponseDTO {
    private UUID id;
    private String title;
    private Integer releaseYear;
    private String posterUrl;
    private Studio studio;
    private Set<DirectorSummaryDTO> directors=new HashSet<>();
    private Set<ActorSummaryDTO> actors = new HashSet<>();
}