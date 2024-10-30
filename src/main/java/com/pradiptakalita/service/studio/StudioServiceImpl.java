package com.pradiptakalita.service.studio;

import com.pradiptakalita.dto.studio.StudioPageResponseDTO;
import com.pradiptakalita.dto.studio.StudioRequestDTO;
import com.pradiptakalita.dto.studio.StudioResponseDTO;
import com.pradiptakalita.dto.studio.StudioSummaryDTO;
import com.pradiptakalita.entity.Studio;
import com.pradiptakalita.exceptions.EntityNotFoundException;
import com.pradiptakalita.mapper.StudioMapper;
import com.pradiptakalita.repository.StudioRepository;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import com.pradiptakalita.service.redisCache.RedisCacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudioServiceImpl implements StudioService{
    private final StudioRepository studioRepository;
    private final CloudinaryService cloudinaryService;
    private final RedisCacheService redisCacheService;

    @Value("${cloudinary.default-profile-url}")
    private String STUDIO_PROFILE_PICTURE_URL ;

    @Value("${cloudinary.folder-names.studios}")
    private String FOLDER_NAME ;

    @Value("${redis.keys.studios.studio-cache-key}")
    private  String STUDIO_CACHE_KEY ;

    @Value("${redis.keys.studios.all-studio-cache-key}")
    private  String ALL_STUDIO_CACHE_KEY ;

    private String getDefaultPictureUrl(){
        return STUDIO_PROFILE_PICTURE_URL;
    }
    private String getDefaultFolderName(){
        return FOLDER_NAME;
    }

    public StudioServiceImpl(StudioRepository studioRepository, CloudinaryService cloudinaryService, RedisCacheService redisCacheService) {
        this.studioRepository = studioRepository;
        this.cloudinaryService = cloudinaryService;
        this.redisCacheService = redisCacheService;
    }

    @Override
    public StudioResponseDTO getStudioById(UUID id) {
        StudioResponseDTO cachedResult = (StudioResponseDTO) redisCacheService.getCache(STUDIO_CACHE_KEY+id);
        if(cachedResult!=null){
            System.out.println("SENDING CACHED RESULT");
            return cachedResult;
        }
        Studio studio = studioRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Studio not found with id: "+id));
        StudioResponseDTO result = StudioMapper.toStudioResponseDTO(studio);
        redisCacheService.saveToCache(STUDIO_CACHE_KEY+id,result);
        return result;
    }

    @Override
    public StudioPageResponseDTO getAllStudio(int page, int size, String sortBy, String order) {
        StudioPageResponseDTO cachedResult = (StudioPageResponseDTO) redisCacheService.getCache(ALL_STUDIO_CACHE_KEY+page+"::"+size+"::"+sortBy+"::"+order);
        if(cachedResult!=null){
            return cachedResult;
        }
        String[] sortByFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortByFields[0]):Sort.Order.desc(sortByFields[0]));
        for(int i=1;i<sortByFields.length;i++){
            sort=sort.and(Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortByFields[0]):Sort.Order.desc(sortByFields[0])));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Studio> studioPage = studioRepository.findAll(pageable);
        List<StudioResponseDTO> studios = studioPage.getContent().stream().map(StudioMapper::toStudioResponseDTO).toList();
        StudioPageResponseDTO result = new StudioPageResponseDTO(
                studios,
                studioPage.getTotalElements(),
                studioPage.getTotalPages(),
                studioPage.getNumber(),
                studioPage.getSize(),
                studioPage.hasNext(),
                studioPage.hasPrevious()
        );
        redisCacheService.saveToCache(ALL_STUDIO_CACHE_KEY+page+"::"+size+"::"+sortBy+"::"+order,result);
        return result;
    }



    @Override
    public StudioResponseDTO createStudio(StudioRequestDTO studioRequestDTO) {
        Studio studio = StudioMapper.toEntity(studioRequestDTO);
        String studioPictureUrl=cloudinaryService.uploadFile(studioRequestDTO.getFile(),getDefaultFolderName(),studioRequestDTO.getPublicId(),getDefaultPictureUrl());
        studio.setStudioProfileUrl(studioPictureUrl);
        Studio savedStudio = studioRepository.save(studio);
        StudioResponseDTO result = StudioMapper.toStudioResponseDTO(savedStudio);
        redisCacheService.saveToCache(STUDIO_CACHE_KEY+savedStudio.getId(),result);
        return result;
    }

    @Override
    public StudioResponseDTO updateStudioById(StudioRequestDTO studioRequestDTO, UUID id) {
        Studio studio = studioRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Studio not found with id: "+id));
        studio.setDescription(studioRequestDTO.getDescription());
        String studioPictureUrl = studio.getStudioProfileUrl();
        if(studioRequestDTO.getFile()!=null){
            cloudinaryService.deleteFile(studio.getPublicId(),getDefaultFolderName());
            studioPictureUrl =cloudinaryService.uploadFile(studioRequestDTO.getFile(),getDefaultFolderName(),studioRequestDTO.getPublicId(),studioPictureUrl);
        }
        studio.setName(studioRequestDTO.getName());
        studio.setStudioProfileUrl(studioPictureUrl);
        Studio updatedStudio = studioRepository.save(studio);
        StudioResponseDTO result = StudioMapper.toStudioResponseDTO(updatedStudio);
        redisCacheService.saveToCache(STUDIO_CACHE_KEY+id,result);
        return result;
    }

    @Override
    public void deleteStudioById(UUID id) {
        Studio studio = studioRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Studio not found with id: "+id));
        cloudinaryService.deleteFile(studio.getPublicId(),getDefaultFolderName());
        studioRepository.deleteById(studio.getId());
        redisCacheService.deleteCache();
    }
}
