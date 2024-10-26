package com.pradiptakalita.mapper;

import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.dto.actor.ActorSummaryDTO;
import com.pradiptakalita.dto.movie.MovieSummaryDTO;
import com.pradiptakalita.entity.Actor;
import com.pradiptakalita.entity.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        actorResponseDTO.setBirthDate(actor.getBirthDate());
        actorResponseDTO.setProfilePictureUrl(actor.getProfilePictureUrl());
        return actorResponseDTO;
    }


    public static ActorSummaryDTO toSummaryDTO(Actor actor){
        ActorSummaryDTO actorSummaryDTO = new ActorSummaryDTO();
        actorSummaryDTO.setId(actor.getId());
        actorSummaryDTO.setName(actor.getName());
        actorSummaryDTO.setProfilePictureUrl(actor.getProfilePictureUrl());
        actorSummaryDTO.setMiniBiography(actor.getMiniBiography());
        actorSummaryDTO.setBirthDate(actor.getBirthDate());
        return actorSummaryDTO;
    }
}
