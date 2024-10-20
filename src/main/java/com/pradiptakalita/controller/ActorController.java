package com.pradiptakalita.controller;

import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.dto.actor.ActorSummaryDTO;
import com.pradiptakalita.service.actor.ActorService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/summary")
    public ResponseEntity<List<ActorSummaryDTO>> getActorSummary(){
        return ResponseEntity.ok().body(actorService.getActorSummary());
    }

    @GetMapping
    public ResponseEntity<List<ActorResponseDTO>> getAllActors(){
        return ResponseEntity.ok().body(actorService.getAllActors());
    }

    @GetMapping("/{actorId}")
    public ResponseEntity<ActorResponseDTO> getActorById(@PathVariable UUID actorId){
        return ResponseEntity.ok().body(actorService.getActorById(actorId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ActorResponseDTO> createActor(@ModelAttribute ActorRequestDTO actorRequestDTO){
        return ResponseEntity.ok().body(actorService.createActor(actorRequestDTO));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,value = "/{actorId}")
    public ResponseEntity<ActorResponseDTO> updateActorById(@ModelAttribute ActorRequestDTO actorRequestDTO,@PathVariable UUID actorId){
        return ResponseEntity.ok().body(actorService.updateActorById(actorRequestDTO,actorId));
    }

    @DeleteMapping("/{actorId}")
    public ResponseEntity<String> deleteActorById(@PathVariable UUID actorId){
        actorService.deleteActorById(actorId);
        return ResponseEntity.ok().body("Actor successfully deleted.");
    }

}
