package com.pradiptakalita.controller;

import com.pradiptakalita.dto.studio.StudioRequestDTO;
import com.pradiptakalita.dto.studio.StudioResponseDTO;
import com.pradiptakalita.service.studio.StudioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/studios")
public class StudioController {

    private final StudioService studioService;

    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    @GetMapping
    public ResponseEntity<List<StudioResponseDTO>> getAllStudios(){
        return ResponseEntity.ok().body(studioService.getAllStudio());
    }

    @GetMapping("/{studioId}")
    public ResponseEntity<StudioResponseDTO> getStudioById(@PathVariable UUID studioId){
        return ResponseEntity.ok().body(studioService.getStudioById(studioId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudioResponseDTO> createStudio(@ModelAttribute @Valid StudioRequestDTO studioRequestDTO){
        if(studioRequestDTO.getFile()==null || studioRequestDTO.getFile().isEmpty()){
            System.out.println("UPLOAD IMAGE");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studioService.createStudio(studioRequestDTO));
    }

    @PutMapping(value = "/{studioId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudioResponseDTO> updateStudioById(@ModelAttribute @Valid StudioRequestDTO studioRequestDTO, @PathVariable UUID studioId){
        return ResponseEntity.ok().body(studioService.updateStudioById(studioRequestDTO,studioId));
    }

    @DeleteMapping("/{studioId}")
    public ResponseEntity<?> deleteStudioById(@PathVariable UUID studioId){
        studioService.deleteStudioById(studioId);
        return ResponseEntity.ok().body("Studio deleted.");
    }

}
