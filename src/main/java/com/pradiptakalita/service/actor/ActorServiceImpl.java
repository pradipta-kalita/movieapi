package com.pradiptakalita.service.actor;

import com.pradiptakalita.dto.actor.ActorPageResponseDTO;
import com.pradiptakalita.dto.actor.ActorRequestDTO;
import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.entity.Actor;
import com.pradiptakalita.exceptions.EntityNotFoundException;
import com.pradiptakalita.mapper.ActorMapper;
import com.pradiptakalita.repository.ActorRepository;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
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

    public ActorServiceImpl(ActorRepository actorRepository, CloudinaryService cloudinaryService) {
        this.actorRepository = actorRepository;
        this.cloudinaryService = cloudinaryService;
    }

    private final String STUDIO_PROFILE_PICTURE_URL = "https://res.cloudinary.com/dfths157i/image/upload/v1729850832/directors/default_picture.png";
    private final String FOLDER_NAME = "actors";

    private String getDefaultPictureUrl(){
        return  STUDIO_PROFILE_PICTURE_URL;
    }
    private String getDefaultFolderName(){
        return FOLDER_NAME;
    }

    @Override
    public ActorResponseDTO getActorById(UUID id) {
        Actor actor = actorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Actor not found with id: "+id));
        return ActorMapper.toResponseDTO(actor);
    }

    @Override
    public ActorPageResponseDTO getAllActors(int page, int size, String sortBy, String order) {
        String[] sortByFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortByFields[0]):Sort.Order.desc(sortByFields[0]));
        for (int i = 1; i < sortByFields.length; i++) {
            sort = sort.and(Sort.by(order.equalsIgnoreCase("asc") ? Sort.Order.asc(sortByFields[i]) : Sort.Order.desc(sortByFields[i])));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Actor> actorPage = actorRepository.findAll(pageable);
        List<ActorResponseDTO> actors = actorPage.getContent().stream().map(ActorMapper::toResponseDTO).toList();
        return new ActorPageResponseDTO(
                actors,
                actorPage.getTotalElements(),
                actorPage.getTotalPages(),
                actorPage.getNumber(),
                actorPage.getSize(),
                actorPage.hasNext(),
                actorPage.hasPrevious()
        );
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
        Actor actor = actorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Actor not found with id: "+id));
        actor.setBirthDate(actorRequestDTO.getBirthDate());
        actor.setMiniBiography(actorRequestDTO.getMiniBiography());
        if(actorRequestDTO.getFile()!=null){
            cloudinaryService.deleteFile(actor.getPublicId(),getDefaultFolderName());
            actor.setName(actorRequestDTO.getName());
            actor.setProfilePictureUrl(cloudinaryService.uploadFile(actorRequestDTO.getFile(),getDefaultFolderName(),actorRequestDTO.getPublicId(),actor.getProfilePictureUrl()));
        }
        return ActorMapper.toResponseDTO(actorRepository.save(actor));
    }

    @Override
    public void deleteActorById(UUID id) {
        Actor actor = actorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Actor not found with id: "+id));
        cloudinaryService.deleteFile(actor.getPublicId(),getDefaultFolderName());
        actorRepository.deleteById(id);
    }
}
