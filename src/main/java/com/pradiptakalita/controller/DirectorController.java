package com.pradiptakalita.controller;

import com.pradiptakalita.dto.director.DirectorPageResponseDTO;
import com.pradiptakalita.dto.director.DirectorRequestDTO;
import com.pradiptakalita.dto.director.DirectorResponseDTO;
import com.pradiptakalita.dto.director.DirectorSummaryDTO;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import com.pradiptakalita.service.director.DirectorService;
import com.pradiptakalita.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/directors")
public class DirectorController {
    private final DirectorService directorService;
    private final CloudinaryService cloudinaryService;

    public DirectorController(DirectorService directorService, CloudinaryService cloudinaryService) {
        this.directorService = directorService;
        this.cloudinaryService = cloudinaryService;
    }


    @GetMapping
    public ResponseEntity<DirectorPageResponseDTO> getAllDirector(@RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
                                                                  @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
                                                                  @RequestParam(defaultValue = AppConstants.NAME_SORT_BY,required = false) String sortBy,
                                                                  @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order){
        return ResponseEntity.ok().body(directorService.getAllDirectors(page,size,sortBy,order));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DirectorResponseDTO> createDirector(@ModelAttribute @Valid DirectorRequestDTO directorRequestDTO){
            DirectorResponseDTO createdDirector = directorService.createDirector(directorRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDirector);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{directorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DirectorResponseDTO> updateDirector(@ModelAttribute @Valid DirectorRequestDTO directorRequestDTO, @PathVariable UUID directorId){
        DirectorResponseDTO directorResponseDTO = directorService.updateDirector(directorRequestDTO,directorId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(directorResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> getDirectorById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(directorService.getDirectorById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{directorId}")
    public ResponseEntity<String> deleteDirectorById(@PathVariable UUID directorId){
        directorService.deleteDirectorById(directorId);
        return ResponseEntity.ok().body("Director successfully deleted");
    }
}
