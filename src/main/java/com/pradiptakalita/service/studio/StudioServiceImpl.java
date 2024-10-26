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

    private final String STUDIO_PROFILE_PICTURE_URL = "https://res.cloudinary.com/dfths157i/image/upload/v1729850832/directors/default_picture.png";
    private final String FOLDER_NAME = "studios";

    private String getDefaultPictureUrl(){
        return  STUDIO_PROFILE_PICTURE_URL;
    }
    private String getDefaultFolderName(){
        return FOLDER_NAME;
    }

    public StudioServiceImpl(StudioRepository studioRepository, CloudinaryService cloudinaryService) {
        this.studioRepository = studioRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public StudioResponseDTO getStudioById(UUID id) {
        Studio studio = studioRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Studio not found with id: "+id));
        return StudioMapper.toStudioResponseDTO(studio);
    }

    @Override
    public StudioPageResponseDTO getAllStudio(int page, int size, String sortBy, String order) {
        String[] sortByFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortByFields[0]):Sort.Order.desc(sortByFields[0]));
        for(int i=1;i<sortByFields.length;i++){
            sort=sort.and(Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortByFields[0]):Sort.Order.desc(sortByFields[0])));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Studio> studioPage = studioRepository.findAll(pageable);
        List<StudioSummaryDTO> studios = studioPage.getContent().stream().map(StudioMapper::toStudioSummaryDTO).toList();

        return new StudioPageResponseDTO(
                studios,
                studioPage.getTotalElements(),
                studioPage.getTotalPages(),
                studioPage.getNumber(),
                studioPage.getSize(),
                studioPage.hasNext(),
                studioPage.hasPrevious()
        );
    }



    @Override
    public StudioResponseDTO createStudio(StudioRequestDTO studioRequestDTO) {
        Studio studio = StudioMapper.toEntity(studioRequestDTO);
        String studioPictureUrl=cloudinaryService.uploadFile(studioRequestDTO.getFile(),getDefaultFolderName(),studioRequestDTO.getPublicId(),getDefaultPictureUrl());
        studio.setStudioProfileUrl(studioPictureUrl);
        Studio savedStudio = studioRepository.save(studio);
        return StudioMapper.toStudioResponseDTO(savedStudio);
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
        return StudioMapper.toStudioResponseDTO(updatedStudio);
    }

    @Override
    public void deleteStudioById(UUID id) {
        Studio studio = studioRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Studio not found with id: "+id));
        cloudinaryService.deleteFile(studio.getPublicId(),getDefaultFolderName());
        studioRepository.deleteById(studio.getId());
    }
}
