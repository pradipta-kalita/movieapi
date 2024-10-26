package com.pradiptakalita.service.actor;


import com.pradiptakalita.dto.actor.ActorPageResponseDTO;
import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.dto.actor.ActorSummaryDTO;

import java.util.List;
import java.util.UUID;

public interface ActorService {
    ActorResponseDTO getActorById(UUID id);
    ActorPageResponseDTO getAllActors(int page, int size, String sortBy, String order);
    ActorResponseDTO createActor(ActorRequestDTO actorRequestDTO);
    ActorResponseDTO updateActorById(ActorRequestDTO actorRequestDTO,UUID id);
    void deleteActorById(UUID id);
}
