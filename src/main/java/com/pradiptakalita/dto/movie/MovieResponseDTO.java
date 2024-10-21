package com.pradiptakalita.dto.movie;

import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.dto.actor.ActorSummaryDTO;
import com.pradiptakalita.dto.director.DirectorResponseDTO;
import com.pradiptakalita.entity.Actor;
import com.pradiptakalita.entity.Director;
import com.pradiptakalita.entity.Studio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private Set<DirectorResponseDTO> directors=new HashSet<>();
    private Set<ActorSummaryDTO> actors = new HashSet<>();
}