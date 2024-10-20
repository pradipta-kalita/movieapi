package com.pradiptakalita.service.actor;


import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.dto.actor.ActorSummaryDTO;

import java.util.List;
import java.util.UUID;

public interface ActorService {
    ActorResponseDTO getActorById(UUID id);
    List<ActorResponseDTO> getAllActors();
    List<ActorSummaryDTO> getActorSummary();
    ActorResponseDTO createActor(ActorRequestDTO actorRequestDTO);
    ActorResponseDTO updateActorById(ActorRequestDTO actorRequestDTO,UUID id);
    void deleteActorById(UUID id);
}
