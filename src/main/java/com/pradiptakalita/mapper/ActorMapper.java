package com.pradiptakalita.mapper;

import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.dto.actor.ActorSummaryDTO;
import com.pradiptakalita.dto.movie.MovieResponseDTO;
import com.pradiptakalita.dto.movie.MovieSummaryDTO;
import com.pradiptakalita.entity.Actor;
import com.pradiptakalita.entity.Movie;

import java.util.HashSet;
import java.util.Set;


public class ActorMapper {
    public static Actor toEntity(ActorRequestDTO actorRequestDTO){
        Actor actor = new Actor();
        actor.setName(actorRequestDTO.getName());
        actor.setMiniBiography(actorRequestDTO.getMiniBiography());
        actor.setBirthDate(actorRequestDTO.getBirthDate());
        return actor;
    }

    public static ActorResponseDTO toResponseDTO(Actor actor) {
        ActorResponseDTO actorResponseDTO = new ActorResponseDTO();
        actorResponseDTO.setId(actor.getId());
        actorResponseDTO.setName(actor.getName());
        actorResponseDTO.setMiniBiography(actor.getMiniBiography());
        actorResponseDTO.setBirthDate(actor.getBirthDate().toString());
        actorResponseDTO.setProfilePictureUrl(actor.getProfilePictureUrl());
        Set<MovieSummaryDTO> movies = new HashSet<>();
        for(Movie movie: actor.getMovies()){
            movies.add(MovieMapper.toMovieSummaryDTO(movie));
        }
        actorResponseDTO.setMovies(movies);
        return actorResponseDTO;
    }


    public static ActorSummaryDTO toSummaryDTO(Actor actor){
        ActorSummaryDTO actorSummaryDTO = new ActorSummaryDTO();
        actorSummaryDTO.setId(actor.getId());
        actorSummaryDTO.setName(actor.getName());
        actorSummaryDTO.setProfilePictureUrl(actor.getProfilePictureUrl());
        actorSummaryDTO.setMiniBiography(actor.getMiniBiography());
        actorSummaryDTO.setBirthDate(actor.getBirthDate().toString());
        return actorSummaryDTO;
    }
}
