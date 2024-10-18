package com.pradiptakalita.mapper;

import com.pradiptakalita.dto.studio.StudioRequestDTO;
import com.pradiptakalita.dto.studio.StudioResponseDTO;
import com.pradiptakalita.entity.Studio;

public class StudioMapper {
    public static Studio toEntity(StudioRequestDTO studioRequestDTO){
        Studio studio = new Studio();
        studio.setName(studioRequestDTO.getName());
        studio.setDescription(studioRequestDTO.getDescription());
        return studio;
    }

    public static StudioResponseDTO studioResponseDTO(Studio studio){
        StudioResponseDTO studioResponseDTO = new StudioResponseDTO();
        studioResponseDTO.setId(studio.getId());
        studioResponseDTO.setName(studio.getName());
        studioResponseDTO.setDescription(studio.getDescription());
        studioResponseDTO.setMovies(studio.getMovies());
        studioResponseDTO.setStudioProfileUrl(studio.getStudioProfileUrl());
        return studioResponseDTO;
    }
}
