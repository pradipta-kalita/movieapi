package com.pradiptakalita.controller;

import com.pradiptakalita.dto.studio.StudioPageResponseDTO;
import com.pradiptakalita.dto.studio.StudioRequestDTO;
import com.pradiptakalita.dto.studio.StudioResponseDTO;
import com.pradiptakalita.dto.studio.StudioSummaryDTO;
import com.pradiptakalita.service.studio.StudioService;
import com.pradiptakalita.utils.AppConstants;
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
    public ResponseEntity<StudioPageResponseDTO> getAllStudios(@RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
                                                               @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
                                                               @RequestParam(defaultValue = AppConstants.NAME_SORT_BY,required = false) String sortBy,
                                                               @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order){
        return ResponseEntity.ok().body(studioService.getAllStudio(page,size,sortBy,order));
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
