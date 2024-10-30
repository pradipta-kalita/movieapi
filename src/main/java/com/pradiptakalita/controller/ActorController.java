package com.pradiptakalita.controller;

import com.pradiptakalita.dto.actor.ActorPageResponseDTO;
import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.service.actor.ActorService;
import com.pradiptakalita.utils.AppConstants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<ActorPageResponseDTO> getAllActors(@RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
                                                             @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
                                                             @RequestParam(defaultValue = AppConstants.NAME_SORT_BY,required = false) String sortBy,
                                                             @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order){
        return ResponseEntity.ok().body(actorService.getAllActors(page,size,sortBy,order));
    }

    @GetMapping("/{actorId}")
    public ResponseEntity<ActorResponseDTO> getActorById(@PathVariable UUID actorId){
        return ResponseEntity.ok().body(actorService.getActorById(actorId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ActorResponseDTO> createActor(@ModelAttribute ActorRequestDTO actorRequestDTO){
        return ResponseEntity.ok().body(actorService.createActor(actorRequestDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{actorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ActorResponseDTO> updateActorById(@ModelAttribute ActorRequestDTO actorRequestDTO, @PathVariable UUID actorId) {
        return ResponseEntity.ok().body(actorService.updateActorById(actorRequestDTO, actorId));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{actorId}")
    public ResponseEntity<String> deleteActorById(@PathVariable UUID actorId){
        actorService.deleteActorById(actorId);
        return ResponseEntity.ok().body("Actor successfully deleted.");
    }

}
