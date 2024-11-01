package com.pradiptakalita.controller;

import com.pradiptakalita.auth.service.JwtService;
import com.pradiptakalita.dto.actor.ActorPageResponseDTO;
import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.service.actor.ActorService;
import com.pradiptakalita.service.rateLimiter.RedisRateLimiterService;
import com.pradiptakalita.utils.AppConstants;
import com.pradiptakalita.utils.RateLimitUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorController {
    private final ActorService actorService;
    private final RedisRateLimiterService redisRateLimiterService;

    public ActorController(ActorService actorService, RedisRateLimiterService redisRateLimiterService) {
        this.actorService = actorService;
        this.redisRateLimiterService = redisRateLimiterService;
    }

    @GetMapping
    public ResponseEntity<ActorPageResponseDTO> getAllActors(@RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
                                                             @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
                                                             @RequestParam(defaultValue = AppConstants.NAME_SORT_BY,required = false) String sortBy,
                                                             @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order,
                                                             @RequestHeader("Authorization") String authHeader,
                                                             HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        return ResponseEntity.ok().body(actorService.getAllActors(page,size,sortBy,order));
    }

    @GetMapping("/{actorId}")
    public ResponseEntity<ActorResponseDTO> getActorById(@PathVariable UUID actorId,
                                                         @RequestHeader("Authorization") String authHeader,
                                                         HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        return ResponseEntity.ok().body(actorService.getActorById(actorId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ActorResponseDTO> createActor(@ModelAttribute ActorRequestDTO actorRequestDTO,
                                                        @RequestHeader("Authorization") String authHeader,
                                                        HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        return ResponseEntity.ok().body(actorService.createActor(actorRequestDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{actorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ActorResponseDTO> updateActorById(@ModelAttribute ActorRequestDTO actorRequestDTO,
                                                            @PathVariable UUID actorId,
                                                            @RequestHeader("Authorization") String authHeader,
                                                            HttpServletRequest request) {
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        return ResponseEntity.ok().body(actorService.updateActorById(actorRequestDTO, actorId));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{actorId}")
    public ResponseEntity<String> deleteActorById(@PathVariable UUID actorId,
                                                  @RequestHeader("Authorization") String authHeader,
                                                  HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForAuthenticatedUser(request,authHeader);
        actorService.deleteActorById(actorId);
        return ResponseEntity.ok().body("Actor successfully deleted.");
    }

}
