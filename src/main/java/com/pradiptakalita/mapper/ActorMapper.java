package com.pradiptakalita.mapper;

import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.entity.Actor;

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
        actorResponseDTO.setMovies(actor.getMovies());
        return actorResponseDTO;
    }
}
