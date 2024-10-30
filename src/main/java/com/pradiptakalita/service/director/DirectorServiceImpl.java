package com.pradiptakalita.service.director;


import com.pradiptakalita.dto.actor.ActorPageResponseDTO;
import com.pradiptakalita.dto.director.DirectorPageResponseDTO;
import com.pradiptakalita.dto.director.DirectorRequestDTO;
import com.pradiptakalita.dto.director.DirectorResponseDTO;
import com.pradiptakalita.dto.director.DirectorSummaryDTO;
import com.pradiptakalita.entity.Director;
import com.pradiptakalita.exceptions.EntityNotFoundException;
import com.pradiptakalita.mapper.DirectorMapper;
import com.pradiptakalita.repository.DirectorRepository;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import com.pradiptakalita.service.redisCache.RedisCacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DirectorServiceImpl implements DirectorService{

    private final DirectorRepository directorRepository;
    private final CloudinaryService cloudinaryService;
    private final RedisCacheService redisCacheService;

    public DirectorServiceImpl(DirectorRepository directorRepository, CloudinaryService cloudinaryService, RedisCacheService redisCacheService) {
        this.directorRepository = directorRepository;
        this.cloudinaryService = cloudinaryService;
        this.redisCacheService = redisCacheService;
    }

    @Value("${cloudinary.default-profile-url}")
    private String DIRECTOR_PROFILE_PICTURE_URL ;

    @Value("${cloudinary.folder-names.directors}")
    private String FOLDER_NAME ;

    @Value("${redis.keys.directors.director-cache-key}")
    private  String DIRECTOR_CACHE_KEY ;

    @Value("${redis.keys.directors.all-director-cache-key}")
    private  String ALL_DIRECTOR_CACHE_KEY ;

    private String getDefaultPictureUrl(){
        return DIRECTOR_PROFILE_PICTURE_URL;
    }
    private String getDefaultFolderName(){
        return FOLDER_NAME;
    }

    @Override
    public DirectorResponseDTO createDirector(DirectorRequestDTO directorRequestDTO) {
        String profilePictureUrl = cloudinaryService.uploadFile(directorRequestDTO.getFile(),getDefaultFolderName(),directorRequestDTO.getPublicId(),getDefaultPictureUrl());
        Director director = DirectorMapper.toEntity(directorRequestDTO);
        director.setProfilePictureUrl(profilePictureUrl);
        Director savedDirector = directorRepository.save(director);
        DirectorResponseDTO result = DirectorMapper.toResponseDTO(savedDirector);
        redisCacheService.saveToCache(DIRECTOR_CACHE_KEY+result.getId(),result);
        return result;
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
                cloudinaryService.deleteFile(director.getPublicId(),getDefaultFolderName());
                String newProfilePictureUrl = cloudinaryService.uploadFile(directorRequestDTO.getFile(),getDefaultFolderName(),directorRequestDTO.getPublicId(),director.getProfilePictureUrl());
                director.setProfilePictureUrl(newProfilePictureUrl);
            }
            Director savedDirector = directorRepository.save(director);
            DirectorResponseDTO result = DirectorMapper.toResponseDTO(savedDirector);
            redisCacheService.saveToCache(DIRECTOR_CACHE_KEY+directorId,result);
            return result;
        }else{
            throw new EntityNotFoundException("Director not found with id :"+directorId);
        }

    }

    @Override
    public DirectorResponseDTO getDirectorById(UUID id) {
        DirectorResponseDTO cachedResult = (DirectorResponseDTO) redisCacheService.getCache(DIRECTOR_CACHE_KEY+id);
        if(cachedResult!=null){
            return cachedResult;
        }
        Director director = directorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Director not found with id :"+id));
        DirectorResponseDTO result = DirectorMapper.toResponseDTO(director);
        redisCacheService.saveToCache(DIRECTOR_CACHE_KEY+id,result);
        return result;
    }

    @Override
    public void deleteDirectorById(UUID id) {
        Director director = directorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Director not found with id :"+id));
        cloudinaryService.deleteFile(director.getPublicId(),getDefaultFolderName());
        directorRepository.deleteById(director.getId());
        redisCacheService.deleteCache();
    }



    @Override
    public DirectorPageResponseDTO getAllDirectors(int page, int size, String sortBy, String order) {
        DirectorPageResponseDTO cachedResult =(DirectorPageResponseDTO) redisCacheService.getCache(ALL_DIRECTOR_CACHE_KEY+page+"::"+size+"::"+sortBy+"::"+order);
        if (cachedResult!=null){
            System.out.println("SENDING CACHED RESULT");
            return cachedResult;
        }
        String[] sortFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc") ? Sort.Order.asc(sortFields[0]) : Sort.Order.desc(sortFields[0]));
        for (int i = 1; i < sortFields.length; i++) {
            sort = sort.and(Sort.by(order.equalsIgnoreCase("asc") ? Sort.Order.asc(sortFields[i]) : Sort.Order.desc(sortFields[i])));
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Director> directorPage = directorRepository.findAll(pageable);
        List<DirectorResponseDTO> directorResponseDTOS = directorPage.getContent().stream().map(DirectorMapper::toResponseDTO).toList();
        DirectorPageResponseDTO result = new DirectorPageResponseDTO(
                directorResponseDTOS,
                directorPage.getTotalElements(),
                directorPage.getTotalPages(),
                directorPage.getNumber(),
                directorPage.getSize(),
                directorPage.hasNext(),
                directorPage.hasPrevious()
        );
        redisCacheService.saveToCache(ALL_DIRECTOR_CACHE_KEY+page+"::"+size+"::"+sortBy+"::"+order,result);
        return result;
    }
}
