package com.pradiptakalita.service.actor;

import com.pradiptakalita.dto.actor.ActorPageResponseDTO;
import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.entity.Actor;
import com.pradiptakalita.exceptions.EntityNotFoundException;
import com.pradiptakalita.mapper.ActorMapper;
import com.pradiptakalita.repository.ActorRepository;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import com.pradiptakalita.service.redisCache.RedisCacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ActorServiceImpl implements ActorService{
    private final ActorRepository actorRepository;
    private final CloudinaryService cloudinaryService;
    private final RedisCacheService redisCacheService;

    public ActorServiceImpl(ActorRepository actorRepository, CloudinaryService cloudinaryService, RedisCacheService redisCacheService) {
        this.actorRepository = actorRepository;
        this.cloudinaryService = cloudinaryService;
        this.redisCacheService = redisCacheService;
    }

    @Value("${cloudinary.default-profile-url}")
    private String STUDIO_PROFILE_PICTURE_URL ;

    @Value("${cloudinary.folder-names.actors}")
    private String FOLDER_NAME ;

    @Value("${redis.keys.actors.actor-cache-key}")
    private  String ACTOR_CACHE_KEY ;

    @Value("${redis.keys.actors.all-actor-cache-key}")
    private  String ALL_ACTOR_CACHE_KEY ;

    private String getDefaultPictureUrl(){
        return  STUDIO_PROFILE_PICTURE_URL;
    }
    private String getDefaultFolderName(){
        return FOLDER_NAME;
    }

    @Override
    public ActorResponseDTO getActorById(UUID id) {
        ActorResponseDTO cachedResult = (ActorResponseDTO) redisCacheService.getCache("actor::"+id);
        if(cachedResult!=null){
            System.out.println("SENDING CACHED RESULT");
            return cachedResult;
        }
        Actor actor = actorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Actor not found with id: "+id));
        ActorResponseDTO result= ActorMapper.toResponseDTO(actor);
        redisCacheService.saveToCache(ACTOR_CACHE_KEY+id,result);
        return result;
    }

    @Override
    public ActorPageResponseDTO getAllActors(int page, int size, String sortBy, String order) {
        ActorPageResponseDTO cachedResult =(ActorPageResponseDTO) redisCacheService.getCache(ALL_ACTOR_CACHE_KEY+page+"::"+size+"::"+sortBy+"::"+order);
        if (cachedResult!=null){
            System.out.println("SENDING CACHED RESULT");
            return cachedResult;
        }
        String[] sortByFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortByFields[0]):Sort.Order.desc(sortByFields[0]));
        for (int i = 1; i < sortByFields.length; i++) {
            sort = sort.and(Sort.by(order.equalsIgnoreCase("asc") ? Sort.Order.asc(sortByFields[i]) : Sort.Order.desc(sortByFields[i])));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Actor> actorPage = actorRepository.findAll(pageable);
        List<ActorResponseDTO> actors = actorPage.getContent().stream().map(ActorMapper::toResponseDTO).toList();
        ActorPageResponseDTO result = new ActorPageResponseDTO(
                actors,
                actorPage.getTotalElements(),
                actorPage.getTotalPages(),
                actorPage.getNumber(),
                actorPage.getSize(),
                actorPage.hasNext(),
                actorPage.hasPrevious()
        );
        redisCacheService.saveToCache(ALL_ACTOR_CACHE_KEY+page+"::"+size+"::"+sortBy+"::"+order,result);
        return result;
    }

    @Override
    public ActorResponseDTO createActor(ActorRequestDTO actorRequestDTO) {
        Actor actor = ActorMapper.toEntity(actorRequestDTO);
        String profilePictureUrl = cloudinaryService.uploadFile(actorRequestDTO.getFile(),getDefaultFolderName(),actorRequestDTO.getPublicId(),getDefaultPictureUrl());
        actor.setProfilePictureUrl(profilePictureUrl);
        ActorResponseDTO result = ActorMapper.toResponseDTO(actorRepository.save(actor));
        redisCacheService.saveToCache(ACTOR_CACHE_KEY+result.getId(),result);
        return result;
    }

    @Override
    public ActorResponseDTO updateActorById(ActorRequestDTO actorRequestDTO, UUID id) {
        Actor actor = actorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Actor not found with id: "+id));
        actor.setBirthDate(actorRequestDTO.getBirthDate());
        actor.setMiniBiography(actorRequestDTO.getMiniBiography());
        actor.setName(actorRequestDTO.getName());
        if(actorRequestDTO.getFile()!=null){
            cloudinaryService.deleteFile(actor.getPublicId(),getDefaultFolderName());
            actor.setName(actorRequestDTO.getName());
            actor.setProfilePictureUrl(cloudinaryService.uploadFile(actorRequestDTO.getFile(),getDefaultFolderName(),actorRequestDTO.getPublicId(),actor.getProfilePictureUrl()));
        }
        Actor savedActor = actorRepository.save(actor);
        ActorResponseDTO result = ActorMapper.toResponseDTO(savedActor);
        redisCacheService.saveToCache(ACTOR_CACHE_KEY+id,result);
        return result;
    }

    @Override
    public void deleteActorById(UUID id) {
        Actor actor = actorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Actor not found with id: "+id));
        cloudinaryService.deleteFile(actor.getPublicId(),getDefaultFolderName());
        redisCacheService.deleteCache();
        actorRepository.deleteById(id);
    }
}
