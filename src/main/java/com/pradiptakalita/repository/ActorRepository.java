package com.pradiptakalita.repository;

import com.pradiptakalita.dto.actor.ActorSummaryDTO;
import com.pradiptakalita.dto.director.DirectorSummaryDTO;
import com.pradiptakalita.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActorRepository extends JpaRepository<Actor, UUID> {
    @Query("SELECT new com.pradiptakalita.dto.actor.ActorSummaryDTO(a.id, a.name, a.profilePictureUrl) FROM Actor a")
    List<ActorSummaryDTO> getActorSummary();
}
