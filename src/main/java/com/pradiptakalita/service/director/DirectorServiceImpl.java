package com.pradiptakalita.service.director;


import com.pradiptakalita.dto.director.DirectorRequestDTO;
import com.pradiptakalita.dto.director.DirectorResponseDTO;
import com.pradiptakalita.dto.director.DirectorSummaryDTO;
import com.pradiptakalita.entity.Director;
import com.pradiptakalita.mapper.DirectorMapper;
import com.pradiptakalita.repository.DirectorRepository;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DirectorServiceImpl implements DirectorService{

    private final DirectorRepository directorRepository;
    private final CloudinaryService cloudinaryService;

    private final String DIRECTOR_PROFILE_PICTURE_URL = "https://res.cloudinary.com/dfths157i/image/upload/v1729199808/directors/default_pfp.jpg";
    private final String FOLDER_NAME = "directors";

    private String getDefaultPictureUrl(){
        return DIRECTOR_PROFILE_PICTURE_URL;
    }
    private String getDefaultFolderName(){
        return FOLDER_NAME;
    }

    public DirectorServiceImpl(DirectorRepository directorRepository, CloudinaryService cloudinaryService) {
        this.directorRepository = directorRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public DirectorResponseDTO createDirector(DirectorRequestDTO directorRequestDTO) {
        String profilePictureUrl = cloudinaryService.uploadFile(directorRequestDTO.getFile(),getDefaultFolderName(),directorRequestDTO.getPublicId(),getDefaultPictureUrl());
        Director director = DirectorMapper.toEntity(directorRequestDTO);
        director.setProfilePictureUrl(profilePictureUrl);
        Director savedDirector = directorRepository.save(director);
        return DirectorMapper.toResponseDTO(savedDirector);
    }

    @Override
    public DirectorResponseDTO updateDirector(DirectorRequestDTO directorRequestDTO,UUID directorId) {
        Optional<Director> optionalDirector = directorRepository.findById(directorId);
        if(optionalDirector.isPresent()){
            Director director = optionalDirector.get();
            director.setBirthDate(directorRequestDTO.getBirthDate());
            director.setName(directorRequestDTO.getName());
            director.setMiniBiography(directorRequestDTO.getMiniBiography());
            if(directorRequestDTO.getFile()!=null){
                cloudinaryService.deleteFile(director.getPublicId());
                String newProfilePictureUrl = cloudinaryService.uploadFile(directorRequestDTO.getFile(),getDefaultFolderName(),directorRequestDTO.getPublicId(),director.getProfilePictureUrl());
                director.setProfilePictureUrl(newProfilePictureUrl);
            }
            Director savedDirector = directorRepository.save(director);
            return DirectorMapper.toResponseDTO(savedDirector);
        }else{
            throw new RuntimeException("Director doesn't exist");
        }

    }

    @Override
    public DirectorResponseDTO getDirectorById(UUID id) {
        Director director = directorRepository.findById(id).orElseThrow(()->new RuntimeException("Director doesn't exist"));
        return DirectorMapper.toResponseDTO(director);
    }

    @Override
    public void deleteDirectorById(UUID id) {
        Director director = directorRepository.findById(id).orElseThrow(()->new RuntimeException("Director doesn't exist to delete"));
        cloudinaryService.deleteFile(director.getPublicId());
        directorRepository.deleteById(director.getId());
    }

    @Override
    public List<DirectorSummaryDTO> getAllDirectorWithNameAndProfile() {
       return directorRepository.findAllDirectorsNameAndProfilePicture();
    }

    @Override
    public List<DirectorResponseDTO> getAllDirectors() {
        List<Director> directors = directorRepository.findAll();
        List<DirectorResponseDTO> result = new ArrayList<>();
        for(Director director : directors){
            result.add(DirectorMapper.toResponseDTO(director));
        }
        return result;
    }
}
