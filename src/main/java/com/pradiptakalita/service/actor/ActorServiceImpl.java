package com.pradiptakalita.service.actor;

import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.dto.actor.ActorSummaryDTO;
import com.pradiptakalita.entity.Actor;
import com.pradiptakalita.mapper.ActorMapper;
import com.pradiptakalita.repository.ActorRepository;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ActorServiceImpl implements ActorService{
    private final ActorRepository actorRepository;
    private final CloudinaryService cloudinaryService;

    public ActorServiceImpl(ActorRepository actorRepository, CloudinaryService cloudinaryService) {
        this.actorRepository = actorRepository;
        this.cloudinaryService = cloudinaryService;
    }

    private final String STUDIO_PROFILE_PICTURE_URL = "https://res.cloudinary.com/dfths157i/image/upload/v1729199808/directors/default_pfp.jpg";
    private final String FOLDER_NAME = "actors";

    private String getDefaultPictureUrl(){
        return  STUDIO_PROFILE_PICTURE_URL;
    }
    private String getDefaultFolderName(){
        return FOLDER_NAME;
    }

    @Override
    public ActorResponseDTO getActorById(UUID id) {
        Actor actor = actorRepository.findById(id).orElseThrow(()->new RuntimeException("Actor not found."));
        return ActorMapper.toResponseDTO(actor);
    }

    @Override
    public List<ActorResponseDTO> getAllActors() {
        List<Actor> actors = actorRepository.findAll();
        List<ActorResponseDTO> result = new ArrayList<>();
        for (Actor actor : actors){
            result.add(ActorMapper.toResponseDTO(actor));
        }
        return result;
    }

    @Override
    public List<ActorSummaryDTO> getActorSummary() {
        return actorRepository.getActorSummary();
    }

    @Override
    public ActorResponseDTO createActor(ActorRequestDTO actorRequestDTO) {
        Actor actor = ActorMapper.toEntity(actorRequestDTO);
        String profilePictureUrl = cloudinaryService.uploadFile(actorRequestDTO.getFile(),getDefaultFolderName(),actorRequestDTO.getPublicId(),getDefaultPictureUrl());
        actor.setProfilePictureUrl(profilePictureUrl);
        return ActorMapper.toResponseDTO(actorRepository.save(actor));
    }

    @Override
    public ActorResponseDTO updateActorById(ActorRequestDTO actorRequestDTO, UUID id) {
        Actor actor = actorRepository.findById(id).orElseThrow(()->new RuntimeException("Actor not found."));
        actor.setBirthDate(actorRequestDTO.getBirthDate());
        actor.setMiniBiography(actorRequestDTO.getMiniBiography());
        if(actorRequestDTO.getFile()!=null){
            cloudinaryService.deleteFile(actor.getPublicId());
            actor.setName(actorRequestDTO.getName());
            actor.setProfilePictureUrl(cloudinaryService.uploadFile(actorRequestDTO.getFile(),getDefaultFolderName(),actorRequestDTO.getPublicId(),actor.getProfilePictureUrl()));
        }
        return ActorMapper.toResponseDTO(actorRepository.save(actor));
    }

    @Override
    public void deleteActorById(UUID id) {
        Actor actor = actorRepository.findById(id).orElseThrow(()->new RuntimeException("Actor not found."));
        cloudinaryService.deleteFile(actor.getPublicId());
        actorRepository.deleteById(id);
    }
}
