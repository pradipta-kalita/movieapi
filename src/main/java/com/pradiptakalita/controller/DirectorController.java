package com.pradiptakalita.controller;

import com.pradiptakalita.dto.director.DirectorPageResponseDTO;
import com.pradiptakalita.dto.director.DirectorRequestDTO;
import com.pradiptakalita.dto.director.DirectorResponseDTO;
import com.pradiptakalita.dto.director.DirectorSummaryDTO;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import com.pradiptakalita.service.director.DirectorService;
import com.pradiptakalita.service.rateLimiter.RedisRateLimiterService;
import com.pradiptakalita.utils.AppConstants;
import com.pradiptakalita.utils.RateLimitUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    private final RedisRateLimiterService redisRateLimiterService;

    public DirectorController(DirectorService directorService, RedisRateLimiterService redisRateLimiterService) {
        this.directorService = directorService;
        this.redisRateLimiterService = redisRateLimiterService;
    }


    @GetMapping
    public ResponseEntity<DirectorPageResponseDTO> getAllDirector(@RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
                                                                  @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
                                                                  @RequestParam(defaultValue = AppConstants.NAME_SORT_BY,required = false) String sortBy,
                                                                  @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order,
                                                                  @RequestHeader("Authorization") String authHeader,
                                                                  HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        return ResponseEntity.ok().body(directorService.getAllDirectors(page,size,sortBy,order));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DirectorResponseDTO> createDirector(@ModelAttribute @Valid DirectorRequestDTO directorRequestDTO,
                                                              @RequestHeader("Authorization") String authHeader,
                                                              HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(directorService.createDirector(directorRequestDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{directorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DirectorResponseDTO> updateDirector(@ModelAttribute @Valid DirectorRequestDTO directorRequestDTO,
                                                              @PathVariable UUID directorId,
                                                              @RequestHeader("Authorization") String authHeader,
                                                              HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(directorService.updateDirector(directorRequestDTO,directorId));
    }



    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> getDirectorById(@PathVariable UUID id,
                                                               @RequestHeader("Authorization") String authHeader,
                                                               HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(directorService.getDirectorById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{directorId}")
    public ResponseEntity<String> deleteDirectorById(@PathVariable UUID directorId,
                                                     @RequestHeader("Authorization") String authHeader,
                                                     HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        directorService.deleteDirectorById(directorId);
        return ResponseEntity.ok().body("Director successfully deleted");
    }
}
