package com.pradiptakalita.mapper;

import com.pradiptakalita.dto.movie.MovieSummaryDTO;
import com.pradiptakalita.dto.studio.StudioRequestDTO;
import com.pradiptakalita.dto.studio.StudioResponseDTO;
import com.pradiptakalita.dto.studio.StudioSummaryDTO;
import com.pradiptakalita.entity.Movie;
import com.pradiptakalita.entity.Studio;

import java.util.HashSet;
import java.util.Set;

public class StudioMapper {
    public static Studio toEntity(StudioRequestDTO studioRequestDTO){
        Studio studio = new Studio();
        studio.setName(studioRequestDTO.getName());
        studio.setDescription(studioRequestDTO.getDescription());
        return studio;
    }

    public static StudioResponseDTO toStudioResponseDTO(Studio studio){
        StudioResponseDTO studioResponseDTO = new StudioResponseDTO();
        studioResponseDTO.setId(studio.getId());
        studioResponseDTO.setName(studio.getName());
        studioResponseDTO.setDescription(studio.getDescription());
        studioResponseDTO.setStudioProfileUrl(studio.getStudioProfileUrl());
        Set<MovieSummaryDTO> movies = new HashSet<>();
        for(Movie movie : studio.getMovies()){
            movies.add(MovieMapper.toMovieSummaryDTO(movie));
        }
        studioResponseDTO.setMovies(movies);
        return studioResponseDTO;
    }
    public static StudioSummaryDTO toStudioSummaryDTO(Studio studio){
        StudioSummaryDTO studioSummaryDTO = new StudioSummaryDTO();
        studioSummaryDTO.setId(studio.getId());
        studioSummaryDTO.setName(studio.getName());
        studioSummaryDTO.setDescription(studio.getDescription());
        studioSummaryDTO.setStudioProfileUrl(studio.getStudioProfileUrl());
        return studioSummaryDTO;
    }
}
